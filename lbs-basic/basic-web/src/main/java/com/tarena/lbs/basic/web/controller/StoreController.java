package com.tarena.lbs.basic.web.controller;

import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.service.StoreService;
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
}
