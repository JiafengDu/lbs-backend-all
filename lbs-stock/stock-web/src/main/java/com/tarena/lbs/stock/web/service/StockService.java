package com.tarena.lbs.stock.web.service;

import com.tarena.lbs.pojo.stock.param.CouponStockParam;
import com.tarena.lbs.stock.web.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public Boolean initStock(CouponStockParam param) {
        return null;
    }
}
