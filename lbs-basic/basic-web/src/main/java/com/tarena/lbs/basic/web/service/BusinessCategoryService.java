package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.tarena.lbs.basic.web.repository.BusinessCategoryRepository;
import com.tarena.lbs.pojo.basic.po.BusinessCategoryPO;
import com.tarena.lbs.pojo.basic.vo.BusinessCategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessCategoryService {
    @Autowired
    private BusinessCategoryRepository businessCategoryRepository;
    public List<BusinessCategoryVO> allCategories() {
        List<BusinessCategoryPO> pos = businessCategoryRepository.getAll();
        List<BusinessCategoryVO> vos = null;
        if (CollectionUtils.isNotEmpty(pos)) {
            vos = pos.stream().map(po->{
                BusinessCategoryVO vo = new BusinessCategoryVO();
                BeanUtils.copyProperties(po, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        return vos;
    }
}
