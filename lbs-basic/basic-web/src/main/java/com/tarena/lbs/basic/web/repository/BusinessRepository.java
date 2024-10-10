package com.tarena.lbs.basic.web.repository;

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
        //queryWrapper是null值 表示where条件不拼接任何逻辑
        return businessMapper.selectCount(null);
    }


    public List<BusinessPO> getBusinessByPage(BusinessQuery query) {
        //query pageNo pageSize
        int from=(query.getPageNo()-1)*query.getPageSize();
        int size=query.getPageSize();
        //select * from lbs_business limit #{from},#{size}
        return businessMapper.selectPages(from,size);
    }
}
