package com.tarena.lbs.basic.web.service;

import com.tarena.lbs.basic.web.repository.AdminRepository;
import com.tarena.lbs.pojo.passport.param.AdminLoginParam;
import com.tarena.lbs.pojo.passport.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    //数据层的特点:
    //1. 有一个业务表格 就有一套数据层 **Repository **Mapper
    //2. 读写操作的入参和出参 读操作 的入参是查询条件 出参是PO
    @Autowired
    private AdminRepository adminRepository;
    public LoginVO doLogin(AdminLoginParam param) {
        //1.利用param中的phone查询是否存在admin数据

        return null;
    }
}
