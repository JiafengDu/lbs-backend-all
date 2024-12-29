package com.tarena.lbs.basic.web.controller;

import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.basic.web.service.BusinessCategoryService;
import com.tarena.lbs.pojo.basic.vo.BusinessCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BusinessCategoryController {
    @Autowired
    private BusinessCategoryService businessCategoryService;
    @GetMapping("/admin/basic/business/category/list")
    public Result<List<BusinessCategoryVO>> allCategories() {
        List<BusinessCategoryVO> voList = businessCategoryService.allCategories();
        return new Result<>(voList);
    }
}
