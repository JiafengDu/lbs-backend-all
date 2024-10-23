package com.tarena.lbs.marketing.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.api.BasicApi;
import com.tarena.lbs.common.passport.enums.Roles;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.marketing.web.repository.CouponCodeRepository;
import com.tarena.lbs.marketing.web.repository.CouponRepository;
import com.tarena.lbs.marketing.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.pojo.basic.dto.AdminDTO;
import com.tarena.lbs.pojo.marketing.param.CouponParam;
import com.tarena.lbs.pojo.marketing.po.CouponCodePO;
import com.tarena.lbs.pojo.marketing.po.CouponPO;
import com.tarena.lbs.pojo.marketing.vo.CouponVO;
import com.tarena.lbs.pojo.stock.param.CouponStockParam;
import com.tarena.lbs.pojo.stock.po.CouponStockPO;
import com.tarena.lbs.stock.api.StockApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private CouponCodeRepository couponCodeRepository;
    @DubboReference
    private BasicApi basicApi;
    @DubboReference
    private StockApi stockApi;
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
    @Transactional(rollbackFor = Exception.class)
    public void save(CouponParam couponParam) throws BusinessException {
        //1.拿到认证对象
        UserPrinciple userPrinciple=parseUserPrinciple();
        //2.判断权限 业务权限
        Asserts.isTrue(userPrinciple.getRole()!=Roles.SHOP,
                new BusinessException("-2","用户权限不足"));
        //3.利用商家账号id 读取商家详情获取businessId
        Integer adminId = userPrinciple.getId();
        Integer businessId = getAdminBusinessId(adminId);
        //4.新增 优惠券 封装优惠券po 交给优惠券仓储层新增
        CouponPO poParam=assembleCouponPO(couponParam,businessId);
        couponRepository.save(poParam);
        //5.同步生成当前优惠券的券码数据
        //List<CouponCodePO> codePoParams=assembleCouponCodePos(poParam);
        //couponCodeRepository.saveBatch(codePoParams,100);
        //6.同步库存
        initCouponStock(poParam);
    }

    private void initCouponStock(CouponPO poParam) throws BusinessException {
        //使用优惠券 组织一个dubbo调用的入参
        CouponStockParam param=new CouponStockParam();
        param.setCouponId(poParam.getId());
        param.setBusinessId(poParam.getBusinessId());
        param.setNum(poParam.getMaxUsageLimit());
        Boolean result = stockApi.initCouponStock(param);
        //如果调用结果是false 初始化库存没有成功 库存和优惠券数据一致性问题
        Asserts.isTrue(!result,new BusinessException("-2","库存初始化失败"));
    }

    private List<CouponCodePO> assembleCouponCodePos(CouponPO poParam) {
        //根据poParam 的最大发行量 生成同样个数的list元素数组 1000
        Integer couponId = poParam.getId();
        Integer businessId = poParam.getBusinessId();
        Integer limit = poParam.getMaxUsageLimit();
        //按照limit数量去循环生成 list对象
        List<CouponCodePO> poParams=new ArrayList<>();
        for (int i=0;i<limit;i++){
            CouponCodePO po=new CouponCodePO();
            po.setCouponId(couponId);
            po.setBusinessId(businessId);
            //0未分配 1已分配 2已超时
            po.setStatus(0);
            //时间 和优惠券业务代码同步创建 时间值保持和优惠券一致
            po.setCreateAt(poParam.getCreateAt());
            po.setUpdateAt(poParam.getUpdateAt());
            //为每一个券码创建一个唯一的编码字符串 uuid
            String code = UUID.randomUUID().toString().replaceAll("-", "");
            po.setCouponCode(code);
            poParams.add(po);
        }
        log.info("当前list元素总量:{}",poParams.size());
        return poParams;
    }

    private CouponPO assembleCouponPO(CouponParam couponParam, Integer businessId) throws BusinessException {
        CouponPO po=new CouponPO();
        //1.能考背的先拷贝
        BeanUtils.copyProperties(couponParam,po);
        //2.设置商家id
        po.setBusinessId(businessId);
        //3.设置优惠券状态 0 待激活 1待使用 2过期作废
        po.setStatus(1);
        //4.优惠券管理状态 0 启用 1禁用
        po.setEnableStatus(0);
        //5.创建和更新时间
        Date now=new Date();
        po.setCreateAt(now);
        po.setUpdateAt(now);
        //6.入参携带的startDate和endDate 是字符串"2024-10-10 12:00:00"
        try{
            Date start = DateUtils.parseDate(couponParam.getStartDate(),
                    "yyyy-MM-dd HH:mm:ss");
            Date end = DateUtils.parseDate(couponParam.getEndDate(),
                    "yyyy-MM-dd HH:mm:ss");
            po.setEndDate(end);
            po.setStartDate(start);
        }catch (ParseException e){
            throw new BusinessException("-2","入参字符串日期格式不正确");
        }
        return po;
    }
}
