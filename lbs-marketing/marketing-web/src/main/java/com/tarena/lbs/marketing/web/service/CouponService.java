package com.tarena.lbs.marketing.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.api.BasicApi;
import com.tarena.lbs.common.passport.enums.Roles;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.marketing.web.repository.CouponRepository;
import com.tarena.lbs.marketing.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.pojo.basic.dto.AdminDTO;
import com.tarena.lbs.pojo.marketing.param.CouponParam;
import com.tarena.lbs.pojo.marketing.po.CouponPO;
import com.tarena.lbs.pojo.marketing.vo.CouponVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;
    @DubboReference
    private BasicApi basicApi;
    public PageResult<CouponVO> pageList() throws BusinessException {
        //1.解析认证对象 拿到UserPrinciple
        UserPrinciple userPrinciple=parseUserPrinciple();
        //2.判断一下权限
        Asserts.isTrue(userPrinciple.getRole()== Roles.USER,
                new BusinessException("-2","用户权限不足"));
        Integer adminId = userPrinciple.getId();
        //3 使用adminId 查询admin详情才能拿到businessId
        Integer businessId=getAdminBusinessId(adminId);
        log.info("当前登录用户:{},查询商家id:{}",userPrinciple.getNickname(),businessId);
        //4.当前业务封装分页数据
        PageResult<CouponVO> voPage=new PageResult<>();
        voPage.setPageSize(10);
        voPage.setPageNo(1);
        voPage.setTotal(100l);
        List<CouponVO> vos=null;
        List<CouponPO> pos= couponRepository.getCouponsByBizId(businessId);
        if (CollectionUtils.isNotEmpty(pos)){
            vos=pos.stream().map(po->{
                CouponVO vo=new CouponVO();
                BeanUtils.copyProperties(po,vo);
                return vo;
            }).collect(Collectors.toList());
        }
        voPage.setObjects(vos);
        return voPage;
    }

    private Integer getAdminBusinessId(Integer adminId) throws BusinessException {
        AdminDTO dto = basicApi.getAdminDetail(adminId);
        Asserts.isTrue(dto==null,
                new BusinessException("-2","管理员信息不存在"));
        return dto.getBusinessId();
    }

    private UserPrinciple parseUserPrinciple() throws BusinessException {
        //1.解析
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        Asserts.isTrue(userPrinciple==null,new BusinessException("-2","用户认证解析失败"));
        return userPrinciple;
    }

    public void save(CouponParam couponParam) {
        //TODO
    }
}
