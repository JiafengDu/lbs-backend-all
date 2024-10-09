package com.tarena.lbs.basic.web.controller;

import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.basic.web.service.LoginService;
import com.tarena.lbs.pojo.passport.param.AdminLoginParam;
import com.tarena.lbs.pojo.passport.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//单独处理后台的登录业务功能的接口类
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    //一个前端接口请求 对应一个方法
    //后台登录接口
    @PostMapping("/passport/admin/login")
    public Result<LoginVO> login(AdminLoginParam param){
        //如果接口是写操作 更新 新增 删除 没有result返回值的Result<Void>
        //如果接口需要携带查询 数据给前端 才会补充vo的泛型Result<VO> 控制层泛型是什么 业务层就给控制返回什么
        LoginVO vo=loginService.doLogin(param);
        return new Result<>(vo);
    }
}
