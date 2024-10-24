package com.tarena.lbs.marketing.web.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tarena.lbs.marketing.web.mapper.CouponMapper;
import com.tarena.lbs.pojo.marketing.po.CouponPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CouponRepository {
    @Autowired
    private CouponMapper couponMapper;

    public List<CouponPO> getCouponsByBizId(Integer businessId) {
        //select * from coupon where business_id=#{businessId};
        QueryWrapper<CouponPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_id", businessId);
        return couponMapper.selectList(queryWrapper);
    }

    public void save(CouponPO poParam) {
        couponMapper.insert(poParam);
    }

    public CouponPO getCouponById(Integer id) {
        return couponMapper.selectById(id);
    }
}
