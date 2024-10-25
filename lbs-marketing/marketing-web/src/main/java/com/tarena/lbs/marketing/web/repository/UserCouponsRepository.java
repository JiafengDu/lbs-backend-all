package com.tarena.lbs.marketing.web.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tarena.lbs.marketing.web.mapper.UserCouponsMapper;
import com.tarena.lbs.pojo.marketing.po.UserCouponsPO;
import com.tarena.lbs.pojo.marketing.query.UserCouponQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserCouponsRepository {
    @Autowired
    private UserCouponsMapper userCouponsMapper;

    public Long countReceiveCoupons(Integer id, Integer userId) {
        //select count(9) from user_coupons where coupon_id=#{} and user_id=#{}
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("coupon_id", id);
        queryWrapper.eq("user_id", userId);
        return userCouponsMapper.selectCount(queryWrapper);
    }

    public void save(UserCouponsPO userCouponsPO) {
        userCouponsMapper.insert(userCouponsPO);
    }

    public List<UserCouponsPO> getUserCoupons(UserCouponQuery query) {
        QueryWrapper<UserCouponsPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", query.getUserId());
        queryWrapper.eq("status", query.getStatus());
        return userCouponsMapper.selectList(queryWrapper);
    }
}
