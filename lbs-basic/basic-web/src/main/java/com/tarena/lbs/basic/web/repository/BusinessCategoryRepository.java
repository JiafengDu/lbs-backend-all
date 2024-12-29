package com.tarena.lbs.basic.web.repository;

import com.tarena.lbs.basic.web.mapper.BusinessCategoryMapper;
import com.tarena.lbs.pojo.basic.po.BusinessCategoryPO;
import com.tarena.lbs.pojo.basic.vo.BusinessCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class BusinessCategoryRepository {
    @Autowired
    private BusinessCategoryMapper businessCategoryMapper;
    public List<BusinessCategoryPO> getAll() {
        return businessCategoryMapper.selectList(null);
    }
}
