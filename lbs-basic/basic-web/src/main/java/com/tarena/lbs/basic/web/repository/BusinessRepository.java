package com.tarena.lbs.basic.web.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tarena.lbs.basic.web.mapper.BusinessMapper;
import com.tarena.lbs.pojo.basic.po.BusinessPO;
import com.tarena.lbs.pojo.basic.query.BusinessQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BusinessRepository {
    @Autowired
    private BusinessMapper businessMapper;

    public Long count(BusinessQuery query) {
        // queryWrapper=null means no where clause
        return businessMapper.selectCount(null);
    }

    public List<BusinessPO> getBusinessByPage(BusinessQuery query) {
        // get page objects, select * from lbs_business limit from, size
        int from = (query.getPageNo()-1)*query.getPageSize();
        int size = query.getPageSize();
        return businessMapper.selectPages(from, size);
    }
    // For test only at the moment
    public PageInfo<BusinessPO> pageList() {
        PageHelper.startPage(1, 10);
        List<BusinessPO> pos = businessMapper.selectList(null);
        PageInfo<BusinessPO> pageInfo = new PageInfo<>(pos);
        return pageInfo;
    }

    public PageInfo<BusinessPO> getPages(BusinessQuery query) {
        // 1. use the nonempty params in query as the where clause in the sql statement
        QueryWrapper<BusinessPO> queryWrapper = assembleBusinessQuery(query);
        // 2. start PageHelper
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        // 3. sql immediately after PageHelper to ensure thread safety
        List<BusinessPO> pos = businessMapper.selectList(queryWrapper);
        return new PageInfo<>(pos);
    }

    private QueryWrapper<BusinessPO> assembleBusinessQuery(BusinessQuery query) {
        QueryWrapper<BusinessPO> queryWrapper = new QueryWrapper<>();
        // 1. businessName not null -> where business_name like (%#{}%)
        if (StringUtils.isNotBlank(query.getBusinessName())) {
            queryWrapper.like("business_name", query.getBusinessName());
        }
        // 2. businessHeadPhone not null -> where business_head_phone=#{}
        if (StringUtils.isNotBlank(query.getBusinessHeadPhone())) {
            queryWrapper.eq("business_head_phone", query.getBusinessHeadPhone());
        }
        // 3. businessStatus 0,1,2,3,4,5 -> business_status=#{}
        if ((query.getBusinessStatus()!=null)) {
            queryWrapper.eq("business_status", query.getBusinessStatus());
        }
        // 4. startTime not null -> entry_time>#{}
        if (query.getStartingTime()!=null) {
            queryWrapper.gt("entry_time", query.getStartingTime());
        }
        // 5. endTime not null -> entry_time<#{}
        if (query.getEndTime()!=null) {
            queryWrapper.lt("entry_time", query.getEndTime());
        }
        // 6. businessId not null -> where business_id=#{}
        if (query.getBusinessId()!=null) {
            queryWrapper.eq("business_id", query.getBusinessId());
        }
        return queryWrapper;
    }

    public void save(BusinessPO po) {
        businessMapper.insert(po);
    }

    public Long countBusinessName(String businessName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("businessName", businessName);
        return businessMapper.selectCount(queryWrapper);
    }
}
