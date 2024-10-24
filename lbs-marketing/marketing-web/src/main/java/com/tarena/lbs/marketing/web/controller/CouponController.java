package com.tarena.lbs.marketing.web.controller;

import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.marketing.web.service.CouponService;
import com.tarena.lbs.pojo.marketing.param.CouponParam;
import com.tarena.lbs.pojo.marketing.vo.CouponVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 处理和优惠券数据业务有关的接口
 */
@RestController
@Slf4j
public class CouponController {
    @Autowired
    private CouponService couponService;
    // 查询当前商家优惠券分页列表
    @PostMapping("/admin/marketing/coupon/info/list")
    public Result<PageResult<CouponVO>> pageList() throws BusinessException{
        return new Result<>(couponService.pageList());
    }
    //后台商家新增优惠券
    @PostMapping("/admin/marketing/coupon/info/save")
    public Result<Void> addCoupon(@RequestBody CouponParam couponParam)throws BusinessException{
        Long start=System.currentTimeMillis();
        couponService.save(couponParam);
        Long end=System.currentTimeMillis();
        log.info("新增优惠券发行量:{},时间消耗:{}MS",couponParam.getMaxUsageLimit(),end-start);
        return Result.success();
    }
    //使用优惠券iD查询优惠券详情
    @GetMapping("/admin/marketing/coupon/info/detail/{id}")
    public Result<CouponVO> detail(@PathVariable("id") Integer id)
        throws BusinessException{
        return new Result<>(couponService.detail(id));
    }
}
