package com.tarena.lbs.basic.web.controller;

import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.service.BusinessService;
import com.tarena.lbs.pojo.basic.param.BusinessParam;
import com.tarena.lbs.pojo.basic.query.BusinessQuery;
import com.tarena.lbs.pojo.basic.vo.BusinessVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusinessController {
    @Autowired
    private BusinessService businessService;
    @GetMapping("/admin/basic/business/info/list")
    public Result<PageResult<BusinessVO>> pageList(BusinessQuery query) {
        PageResult<BusinessVO> voPages = businessService.pageList(query);
        return new Result<>(voPages);
    }

    @PostMapping("/admin/basic/business/info/save")
    public Result<Void> save(BusinessParam param) throws BusinessException {
        businessService.save(param);
        return Result.success();
    }
}
