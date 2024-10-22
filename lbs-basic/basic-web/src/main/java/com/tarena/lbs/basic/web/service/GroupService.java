package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.repository.AdminRepository;
import com.tarena.lbs.basic.web.repository.UserGroupRepository;
import com.tarena.lbs.basic.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import com.tarena.lbs.pojo.basic.po.UserGroupPO;
import com.tarena.lbs.pojo.basic.vo.UserGroupVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserGroupRepository userGroupRepository;
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
}
