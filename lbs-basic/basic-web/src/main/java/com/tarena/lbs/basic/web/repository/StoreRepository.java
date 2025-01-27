package com.tarena.lbs.basic.web.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tarena.lbs.basic.web.mapper.StoreMapper;
import com.tarena.lbs.pojo.basic.po.StorePO;
import com.tarena.lbs.pojo.basic.query.StoreQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StoreRepository {
    @Autowired
    private StoreMapper storeMapper;

    public PageInfo<StorePO> getPages(StoreQuery query) {
        QueryWrapper<StorePO> queryWrapper = new QueryWrapper<>();
        if (query.getBusinessId()!=null) {
            queryWrapper.eq("business_id", query.getBusinessId());
        }
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<StorePO> pos = storeMapper.selectList(queryWrapper);
        return new PageInfo<>(pos);
    }

    public void save(StorePO storePO) {

    }
}
