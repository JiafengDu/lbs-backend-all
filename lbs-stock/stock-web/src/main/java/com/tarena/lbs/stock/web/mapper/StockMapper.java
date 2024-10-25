package com.tarena.lbs.stock.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tarena.lbs.pojo.stock.po.CouponStockPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface StockMapper extends BaseMapper<CouponStockPO> {
    @Update("update coupon_stock set num=num-#{num} where coupon_id=#{couponId} and num>=#{num}")
    int updateNumByCouponId(@Param("couponId") Integer couponId, @Param("num")Integer num);
}
