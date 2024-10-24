package com.tarena.lbs.marketing.web.controller;

import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.marketing.web.service.ActivityService;
import com.tarena.lbs.pojo.marketing.param.ActivityParam;
import com.tarena.lbs.pojo.marketing.vo.ActivityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * 活动相关功能接口
 */
@RestController
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    //商家活动管理的 分页列表查询
    @PostMapping("/admin/marketing/marketingActivity/info/list")
    public Result<PageResult<ActivityVO>> pageList()
        throws BusinessException {
        return new Result<>(activityService.pageList());
    }
    @PostMapping("/admin/marketing/marketingActivity/info/save")
    public Result<Void> activitySave(@RequestBody ActivityParam param)
            throws BusinessException, ParseException {
        activityService.activitySave(param);
        return Result.success();
    }
    //文章中查看活动的详情
    @GetMapping("/admin/marketing/marketingActivity/info/detail/{id}")
    public Result<ActivityVO> activityDetail(@PathVariable("id") Integer id)
        throws BusinessException {
        return new Result<>(activityService.detail(id));
    }

}
