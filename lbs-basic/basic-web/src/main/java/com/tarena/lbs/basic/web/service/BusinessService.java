package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.repository.BusinessRepository;
import com.tarena.lbs.pojo.basic.po.BusinessPO;
import com.tarena.lbs.pojo.basic.query.BusinessQuery;
import com.tarena.lbs.pojo.basic.vo.BusinessVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Nodes.collect;

public class BusinessService {
    @Autowired
    private BusinessRepository businessRepository;
    public PageResult<BusinessVO> pageList(BusinessQuery query) {
        // 1. initialize voPages
        PageResult<BusinessVO> voPages = new PageResult<>();
        voPages.setPageNo(query.getPageNo());
        voPages.setPageSize(query.getPageSize());
        // 2. voPages need 5 properties: total, objects, pageNo, pageSize, totalPages
        // totalPage is calculated = total%pageSize==0?total/pageSize:total/pageSize+1
        // 2.1 get total number, select count(*) from lbs_business
        Long total = businessRepository.count(query);
        // 2.2 get page objects, select * from lbs_business limit from, size
        List<BusinessPO> pos = businessRepository.getBusinessByPage(query);
        // 3 convert PO to VO
        List<BusinessVO> vos = null;
        if (CollectionUtils.isNotEmpty(pos)) {
            // use stream() api (better than for loop)
            vos = pos.stream().map(po -> {
                BusinessVO vo = new BusinessVO();
                BeanUtils.copyProperties(po, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        voPages.setTotal(total);
        voPages.setObjects(vos);
        return voPages;
    }
}
