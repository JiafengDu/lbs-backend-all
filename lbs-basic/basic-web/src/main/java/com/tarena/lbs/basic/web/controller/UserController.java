package com.tarena.lbs.basic.web.controller;

import com.tarena.lbs.basic.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 手机小程序用户处理
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;
}
