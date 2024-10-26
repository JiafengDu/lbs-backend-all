package com.tarena.lbs.basic.web.controller;

import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.service.StoreService;
import com.tarena.lbs.pojo.basic.param.StoreParam;
import com.tarena.lbs.pojo.basic.param.UserLocationParam;
import com.tarena.lbs.pojo.basic.query.AreaStoreQuery;
import com.tarena.lbs.pojo.basic.query.StoreQuery;
import com.tarena.lbs.pojo.basic.vo.StoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreController {
    @Autowired
    private StoreService storeService;
    //查询店铺的分页列表数据
    @PostMapping("/admin/basic/store/getAll")
    public Result<PageResult<StoreVO>> pageList(@RequestBody StoreQuery query)
        throws BusinessException {
        PageResult<StoreVO> voPage= storeService.pageList(query);
        return new Result<>(voPage);
    }

    @PostMapping("/admin/basic/store/add")
    public Result<Void> save(@RequestBody StoreParam param)
        throws BusinessException {
        storeService.save(param);
        return Result.success();
    }

    //查询 属于当前登录用户 所属商家 和满足区域条件的店铺列表
    @PostMapping("/admin/basic/store/getStoreByCity")
    public Result<PageResult<StoreVO>> getStoreByCity(@RequestBody AreaStoreQuery query)
        throws BusinessException {
        PageResult<StoreVO> voPage= storeService.getStoreByCity(query);
        return new Result<>(voPage);
    }
    //手机小程序定位上报
    @PostMapping("/basic/store/location")
    public Result<Void> location(@RequestBody UserLocationParam param)
        throws BusinessException{
        storeService.location(param);
        return Result.success();
    }

}
