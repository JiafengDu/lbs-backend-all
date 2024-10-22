package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.basic.web.repository.TagRepository;
import com.tarena.lbs.basic.web.repository.UserRepository;
import com.tarena.lbs.basic.web.repository.UserTagsRepository;
import com.tarena.lbs.basic.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.pojo.basic.param.UserTagsParam;
import com.tarena.lbs.pojo.basic.po.TagLibraryPO;
import com.tarena.lbs.pojo.basic.po.UserTagsPO;
import com.tarena.lbs.pojo.basic.vo.TagLibraryVO;
import com.tarena.lbs.pojo.basic.vo.TagVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserTagsRepository userTagsRepository;
    @Autowired
    private UserRepository userRepository;
    public Map<String, List<TagVO>> getTagsByType(Integer tagType) {
        //1.从数据层 把素有的文章的标签查询 List
        List<TagLibraryPO> pos=tagRepository.getTagsBytType(tagType);
        log.info("查询的类型:{},查询到的标签有:{}",tagType,pos);
        //2.单独封装转化
        Map<String,List<TagVO>> mapVo=assembleTags2Map(pos);
        log.info("封装最终的结果map:{}",mapVo);
        return mapVo;
    }

    private Map<String, List<TagVO>> assembleTags2Map(List<TagLibraryPO> pos) {
        //1.pos 有文章的所有标签 包括 美食 路边摊 高档酒店 网红店 一级 二级所有标签
        Map<String,List<TagVO>> mapVo=new HashMap<>();
        //2.先筛选(filter)出pos中的所有一级标签 firstLevelPos["美食","旅游"]
        List<TagLibraryPO> firstLevelPos = pos.stream().filter(po -> po.getTagParentId().equals(0)).collect(Collectors.toList());
        //3.对一级标签进行循环
        firstLevelPos.stream().forEach(firstPo->{
            String firstTagName=firstPo.getTagName();//map的key值 美食  旅游
            Integer firstTagId=firstPo.getId();//不就是 二级标签parentId
            //4.如何通过一级标签 对象 拿到属于他的二级标签列表 筛选一下parentId等于当前一级标签id的所有
            //路边摊 网红店  高档酒店
            //日记 传说 趣闻
            List<TagLibraryPO> secondLevelPos =
                    pos.stream().filter(po -> po.getTagParentId().equals(firstTagId)).collect(Collectors.toList());
            //5.将当前的二级标签pos 转化成TagVO格式
            List<TagVO> value = secondLevelPos.stream().map(po -> {
                TagVO vo = new TagVO();
                //一级标签名字
                vo.setTagCategoryName(firstTagName);
                TagLibraryVO voTag = new TagLibraryVO();
                BeanUtils.copyProperties(po, voTag);
                vo.setTagLibraryBO(voTag);
                return vo;
            }).collect(Collectors.toList());
            mapVo.put(firstTagName, value);
        });
        return mapVo;
    }

    public void bindUserTags(UserTagsParam param) throws BusinessException {
        //1.拿到登录用户的id
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        Asserts.isTrue(userPrinciple==null,new BusinessException("-2","用户认证失败"));
        param.setUserId(userPrinciple.getId());
        //2.本次是一个新增数据操作 保证幂等 直接删除旧数据
        userTagsRepository.deleteByUserId(param.getUserId());
        //3.封装 实现批量新增
        //比如 userId=1 tagIds=1,2,3 封装3条数据到外键表1-1 1-2 1-3
        List<UserTagsPO> poParams=null;
        //从入参param 拿到tagIds 解析
        String tagIds = param.getTagIds();
        if (StringUtils.isNotEmpty(tagIds)){
            String[] split = tagIds.split(",");
            if (split!=null&&split.length>0){
                log.info("当前入参 标签id不为空:{}",split);
                poParams=Arrays.stream(split).map(tagId->{
                    UserTagsPO po=new UserTagsPO();
                    po.setUserId(param.getUserId());
                    po.setTagId(Integer.parseInt(tagId));
                    return po;
                }).collect(Collectors.toList());
            }
        }
        if (CollectionUtils.isNotEmpty(poParams)){
            //可用的数据持久层封装 非空的 批量写入 Mybatis-plus也给我们准备了 更多的操作方法
            //saveBatch有2个重载方法 第一个就是一次性批量 写入所有list元素
            //saveBatch第二个方法 有个属性 int batchSize 如果元素过多,可以分批次写入
            userTagsRepository.saveBatch(poParams);
        }
        //4.更新用户状态 status=1
        userRepository.updateStatus(param.getUserId(),1);

    }
}
