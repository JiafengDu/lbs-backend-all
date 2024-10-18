package com.tarena.lbs.basic.web.service;

import com.tarena.lbs.basic.web.repository.TagRepository;
import com.tarena.lbs.pojo.basic.po.TagLibraryPO;
import com.tarena.lbs.pojo.basic.vo.TagLibraryVO;
import com.tarena.lbs.pojo.basic.vo.TagVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TagService {
    @Autowired
    private TagRepository tagRepository;
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
}
