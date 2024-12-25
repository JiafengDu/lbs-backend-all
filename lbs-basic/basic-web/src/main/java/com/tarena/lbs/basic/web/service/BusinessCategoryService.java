package com.tarena.lbs.basic.web.service;

import com.tarena.lbs.basic.web.repository.BusinessCategoryRepository;
import com.tarena.lbs.pojo.basic.vo.BusinessCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BusinessCategoryService {
    @Autowired
    private BusinessCategoryRepository businessCategoryRepository;
    public List<BusinessCategoryVO> list() {
        List<BusinessCategoryVO> voList = businessCategoryRepository.list();
        return voList;
    }
}
