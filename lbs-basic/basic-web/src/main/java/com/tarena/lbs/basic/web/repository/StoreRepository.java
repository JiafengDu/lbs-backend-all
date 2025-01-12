package com.tarena.lbs.basic.web.repository;

import com.tarena.lbs.basic.web.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StoreRepository {
    @Autowired
    private StoreMapper storeMapper;
}
