package com.tarena.lbs.stock.web.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tarena.lbs.pojo.stock.po.CouponStockPO;
import com.tarena.lbs.stock.web.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StockRepository {
    @Autowired
    private StockMapper stockMapper;

    public Long countStockByCouponId(Integer couponId) {
        //select count(0) from coupon_stock where coupon_id=#{}
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("coupon_id", couponId);
        return stockMapper.selectCount(queryWrapper);
    }

    public int save(CouponStockPO poParam) {
        return stockMapper.insert(poParam);
    }
}
