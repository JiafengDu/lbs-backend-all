package com.tarena.lbs.basic.web.controller;

import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.service.BusinessService;
import com.tarena.lbs.pojo.basic.query.BusinessQuery;
import com.tarena.lbs.pojo.basic.vo.BusinessVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理和商家相关的接口功能
 */
@RestController
public class BusinessController {
    @Autowired
    private BusinessService businessService;
    //商家的分页列表查询
    //读数据入参 单个属性 id name 对象数据 query结尾
    @GetMapping("/admin/basic/business/info/list")
    public Result<PageResult<BusinessVO>> pageList(BusinessQuery query){
        PageResult<BusinessVO> voPages= businessService.pageList(query);
        return new Result<>(voPages);
    }
}




















