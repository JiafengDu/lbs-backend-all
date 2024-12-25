package com.tarena.lbs.basic.web.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tarena.lbs.basic.web.mapper.BusinessMapper;
import com.tarena.lbs.pojo.basic.po.BusinessPO;
import com.tarena.lbs.pojo.basic.query.BusinessQuery;
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
}
