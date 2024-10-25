package com.tarena.lbs.stock.api;

import com.tarena.lbs.pojo.stock.param.CouponStockParam;

public interface StockApi {
    //初始化库存
    Boolean initCouponStock(CouponStockParam param);
    //获取优惠券 库存剩余数量
    Integer getCouponStock(Integer couponId);
    //扣减某一个优惠券的库存
    Boolean reduceStock(Integer couponId,Integer num);
}
