package com.tarena.lbs.basic.web.controller;

import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.service.GroupService;
import com.tarena.lbs.pojo.basic.vo.UserGroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
}
