package com.tarena.lbs.basic.web.controller;

import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.basic.web.service.UserService;
import com.tarena.lbs.pojo.basic.param.UserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 手机小程序用户处理
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    //小程序新增注册
    @PostMapping("/basic/user/info/register")
    public Result<Void> register(@RequestBody UserParam param)
        throws BusinessException{
        userService.register(param);
        return Result.success();
    }
}
