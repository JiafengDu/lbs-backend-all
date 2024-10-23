package com.tarena.lbs.stock.web.repository;

import com.tarena.lbs.stock.web.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StockRepository {
    @Autowired
    private StockMapper stockMapper;
}
