package com.tarena.lbs.basic.web.controller;

import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.basic.web.service.LoginService;
import com.tarena.lbs.pojo.passport.param.AdminLoginParam;
import com.tarena.lbs.pojo.passport.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private com.tarena.lbs.basic.web.service.LoginService LoginService;
    @PostMapping("/passport/admin/login")
    public Result<LoginVO> login(AdminLoginParam param) {
        LoginVO vo = loginService.doLogin(param);
        return new Result<>(vo);
    }
}
