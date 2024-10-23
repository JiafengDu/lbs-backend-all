package com.tarena.lbs.marketing.web.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tarena.lbs.marketing.web.mapper.CouponCodeMapper;
import com.tarena.lbs.pojo.marketing.po.CouponCodePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CouponCodeRepository extends ServiceImpl<CouponCodeMapper, CouponCodePO> {
    @Autowired
    private CouponCodeMapper couponCodeMapper;
}
