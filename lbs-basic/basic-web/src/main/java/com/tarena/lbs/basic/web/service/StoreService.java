package com.tarena.lbs.basic.web.service;

import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.repository.StoreRepository;
import com.tarena.lbs.pojo.basic.query.StoreQuery;
import com.tarena.lbs.pojo.basic.vo.StoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    public PageResult<StoreVO> pageList(StoreQuery query) {
        return null;
    }
}
