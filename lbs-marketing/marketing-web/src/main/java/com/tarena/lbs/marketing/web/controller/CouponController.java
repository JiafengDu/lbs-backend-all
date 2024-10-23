package com.tarena.lbs.marketing.web.controller;

import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.marketing.web.service.CouponService;
import com.tarena.lbs.pojo.marketing.vo.CouponVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理和优惠券数据业务有关的接口
 */
@RestController
public class CouponController {
    @Autowired
    private CouponService couponService;
    // 查询当前商家优惠券分页列表
    @PostMapping("/admin/marketing/coupon/info/list")
    public Result<PageResult<CouponVO>> pageList() throws BusinessException{
        return new Result<>(couponService.pageList());
    }
}
