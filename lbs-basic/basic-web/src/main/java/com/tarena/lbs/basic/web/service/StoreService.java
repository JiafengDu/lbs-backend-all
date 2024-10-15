package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.github.pagehelper.PageInfo;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.repository.AdminRepository;
import com.tarena.lbs.basic.web.repository.StoreRepository;
import com.tarena.lbs.basic.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.common.passport.enums.Roles;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import com.tarena.lbs.pojo.basic.po.StorePO;
import com.tarena.lbs.pojo.basic.query.StoreQuery;
import com.tarena.lbs.pojo.basic.vo.AdminVO;
import com.tarena.lbs.pojo.basic.vo.StoreVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private AdminRepository adminRepository;
    public PageResult<StoreVO> pageList(StoreQuery query) throws BusinessException {
        //1.获取登录用户的认证对象
        UserPrinciple userPrinciple=getUserPrinciple();
        //2.获取角色判断是否要在query条件中 添加businessId
        Roles role = userPrinciple.getRole();//ADMIN SHOP
        if (role==Roles.SHOP){
            log.info("当前角色是SHOP,需要拼接商家ID");
            //3.利用账号的id 查询账号详情 详情里包含所属商家 busnessId
            AdminPO adminPO = adminRepository.getAdminById(userPrinciple.getId());
            Asserts.isTrue(adminPO==null,new BusinessException("-2","商家账号不存在"));
            //babusinessId填补到查询条件query
            query.setBusinessId(adminPO.getBusinessId());
        }
        //4.封装完整的查询分页的业务逻辑
        PageResult<StoreVO> voPage=doPageList(query);
        return voPage;
    }

    private PageResult<StoreVO> doPageList(StoreQuery query) {
        PageResult<StoreVO> voPage=new PageResult<>();
        //total objects pageNo pageSize totalPage
        PageInfo<StorePO> pageInfo= storeRepository.getPages(query);
        //pageNo pageSize total
        voPage.setPageNo(pageInfo.getPageNum());
        voPage.setPageSize(pageInfo.getPageSize());
        voPage.setTotal(pageInfo.getTotal());
        List<StoreVO> vos=null;
        List<StorePO> pos = pageInfo.getList();
        if (CollectionUtils.isNotEmpty(pos)){
            vos=pos.stream().map(po->{
                StoreVO vo=new StoreVO();
                BeanUtils.copyProperties(po,vo);
                return vo;
            }).collect(Collectors.toList());
        }
        voPage.setObjects(vos);
        return voPage;
    }

    private UserPrinciple getUserPrinciple() throws BusinessException {
        //1.解析
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        Asserts.isTrue(userPrinciple==null,new BusinessException("-2","用户认证解析失败"));
        return userPrinciple;
    }
}
