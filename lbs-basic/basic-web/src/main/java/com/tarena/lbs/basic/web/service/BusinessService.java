package com.tarena.lbs.basic.web.service;

import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.pojo.basic.query.BusinessQuery;
import com.tarena.lbs.pojo.basic.vo.BusinessVO;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {
    public PageResult<BusinessVO> pageList(BusinessQuery query) {
        return null;
    }
}
