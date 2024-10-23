package com.tarena.lbs.stock.api;

import com.tarena.lbs.pojo.stock.param.CouponStockParam;

public interface StockApi {
    Boolean initCouponStock(CouponStockParam param);
}
