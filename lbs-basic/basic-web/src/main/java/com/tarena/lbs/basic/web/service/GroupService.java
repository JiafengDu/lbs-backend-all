package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.repository.AdminRepository;
import com.tarena.lbs.basic.web.repository.UserGroupRepository;
import com.tarena.lbs.basic.web.repository.UserTagsRepository;
import com.tarena.lbs.basic.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.pojo.basic.param.UserGroupParam;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import com.tarena.lbs.pojo.basic.po.UserGroupPO;
import com.tarena.lbs.pojo.basic.vo.UserGroupVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private UserTagsRepository userTagsRepository;
    public PageResult<UserGroupVO> pageList() throws BusinessException {
        //1.封装一个分页假数据 pageNo pageSize total 装配一下所有list
        PageResult<UserGroupVO> voPage=new PageResult<>();
        voPage.setPageNo(1);
        voPage.setPageSize(10);
        voPage.setTotal(100L);
        //2.调用仓储层查询所有人群数据 关注一下 人群所属的商家
        //解析认证对象 拿到id 查询账号详情 拿到商家id
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        Asserts.isTrue(userPrinciple==null,new BusinessException("-2","用户认证解析失败"));
        AdminPO adminPO = adminRepository.getAdminById(userPrinciple.getId());
        Asserts.isTrue(adminPO==null,new BusinessException("-2","商家账号信息不存在"));
        Integer businessId=adminPO.getBusinessId();
        List<UserGroupPO> pos= userGroupRepository.getGroupsByBusinessId(businessId);
        //3 封装返回值
        List<UserGroupVO> vos=null;
        if (CollectionUtils.isNotEmpty(pos)){
            vos=pos.stream().map(po->{
                UserGroupVO vo=new UserGroupVO();
                BeanUtils.copyProperties(po,vo);
                return vo;
            }).collect(Collectors.toList());
        }
        voPage.setObjects(vos);
        return voPage;
    }

    public void save(UserGroupParam param) throws BusinessException {
        //1.封装一个po对象 该补充的都补充 businessId 利用认证对象获取 补充创建时间
        UserGroupPO po=new UserGroupPO();
        BeanUtils.copyProperties(param,po);
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        Asserts.isTrue(userPrinciple==null,new BusinessException("-2","用户认证解析失败"));
        AdminPO adminPO = adminRepository.getAdminById(userPrinciple.getId());
        Asserts.isTrue(adminPO==null,new BusinessException("-2","商家账号信息不存在"));
        Integer businessId=adminPO.getBusinessId();

        po.setBusinessId(businessId);
        po.setCreateAt(new Date());
        //2.调用仓储层 save新增
        userGroupRepository.save(po);
    }

    public List<Integer> getUserGroups(Integer userId, Integer businessId) {
        //1.先试用userId查询 user所关联的所有标签id
        List<Integer> userTagIds=null;
        userTagIds=userTagsRepository.getUserTagsByUserId(userId);
        //2.在使用这个标签id的集合 查询所有包含其中至少一个标签的人群分组 满足businessId
        List<Integer> userGroupIds=null;
        if (CollectionUtils.isNotEmpty(userTagIds)){
            userGroupIds=userGroupRepository.getUserGroupsByTagIdsAndBusinessId(userTagIds,businessId);
        }
        return userGroupIds;//有可能是空
    }
}
