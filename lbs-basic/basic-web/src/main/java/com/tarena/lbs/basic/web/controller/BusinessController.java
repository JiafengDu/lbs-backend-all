package com.tarena.lbs.basic.web.controller;

import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.service.BusinessService;
import com.tarena.lbs.pojo.basic.param.BusinessParam;
import com.tarena.lbs.pojo.basic.query.BusinessQuery;
import com.tarena.lbs.pojo.basic.vo.BusiStoreVO;
import com.tarena.lbs.pojo.basic.vo.BusinessVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 处理和商家相关的接口功能
 */
@RestController
@Slf4j
public class BusinessController {
    @Autowired
    private BusinessService businessService;
    //商家的分页列表查询
    //读数据入参 单个属性 id name 对象数据 query结尾
    @GetMapping("/admin/basic/business/info/list")
    public Result<PageResult<BusinessVO>> pageList(BusinessQuery query)
            throws BusinessException {
        PageResult<BusinessVO> voPages= businessService.pageList(query);
        return new Result<>(voPages);
    }
    //表单填写完毕 新增提交商家数据保存
    //form请求表单格式参数 key=value&key=value
    //请求体携带json {"key","value"}
    //lbs智慧营销项目中 写操作 如果多个属性 命名类型Param后缀
    @PostMapping("/admin/basic/business/info/save")
    public Result<Void> save(@RequestBody BusinessParam param) throws BusinessException {
        businessService.save(param);
        return Result.success();//code=0 success=true
    }
    //商家的业务中 活动查询商家 和旗下店铺列表封装
    @GetMapping("/basic/business/info/detail/{id}")
    public Result<BusiStoreVO> busiStoreDetail(@PathVariable("id") Integer businessId)
        throws BusinessException {
        Long start=System.currentTimeMillis();
        BusiStoreVO vo=businessService.busiStoreDetail(businessId);
        Long end=System.currentTimeMillis();
        log.info("商家详情查询耗时:{}MS",end-start);
        return new Result<>(vo);
    }
}




















