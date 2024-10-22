package com.tarena.lbs.basic.web.controller;

import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.service.GroupService;
import com.tarena.lbs.pojo.basic.param.UserGroupParam;
import com.tarena.lbs.pojo.basic.vo.UserGroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户人群业务相关
 */
@RestController
public class GroupController {
    @Autowired
    private GroupService groupService;
    //人群数据分页列表
    @PostMapping("/admin/basic/userGroup/list")
    public Result<PageResult<UserGroupVO>> pageList()
        throws BusinessException{
        return new Result<>(groupService.pageList());
    }
    //人群新增 insert  lbs_user_group
    @PostMapping("/admin/basic/userGroup/save")
    public Result<Void> save(@RequestBody UserGroupParam param)throws BusinessException
    {
        groupService.save(param);
        return Result.success();
    }
}
