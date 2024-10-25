package com.tarena.lbs.marketing.web.controller;

import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.marketing.web.service.CouponService;
import com.tarena.lbs.pojo.marketing.param.CouponParam;
import com.tarena.lbs.pojo.marketing.param.UserCouponsParam;
import com.tarena.lbs.pojo.marketing.query.UserCouponCodeQuery;
import com.tarena.lbs.pojo.marketing.query.UserCouponQuery;
import com.tarena.lbs.pojo.marketing.vo.CouponVO;
import com.tarena.lbs.pojo.marketing.vo.UserCouponsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 处理和优惠券数据业务有关的接口
 */
@RestController
@Slf4j
@Api(tags="优惠券模块")
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
    //前台小程序用户 领取优惠券
    @ApiOperation(value = "领取优惠券")
    @PostMapping("/marketing/user/receive/save")
    public Result<Void> receiveCoupon(@RequestBody UserCouponsParam param)
        throws BusinessException{
        Long start=System.currentTimeMillis();
        couponService.receiveCoupon(param);
        Long end=System.currentTimeMillis();
        log.info("用户领取优惠券耗时:{}MS",end-start);
        return Result.success();
    }
    // 前台小程序用户领取优惠券列表
    @PostMapping("/marketing/user/receive/list")
    public Result<PageResult<UserCouponsVO>> receiveList(@RequestBody UserCouponQuery couponQuery)throws BusinessException{
        return new Result<>(couponService.receiveList(couponQuery));
    }
    //查看某一个优惠券码的二维码图片 查看券码详情(二维码只是一个url图片属性)
    //疑问: 大量的接口都是查询功能 为什么使用post
    //REST风格 建议 按照http协议最初设计目的 使用不同请求
    @PostMapping("/marketing/user/receive/detail")
    public Result<UserCouponsVO> receiveDetail(@RequestBody UserCouponCodeQuery query)
        throws BusinessException{
        return new Result<>(couponService.receiveDetail(query));
    }


}
