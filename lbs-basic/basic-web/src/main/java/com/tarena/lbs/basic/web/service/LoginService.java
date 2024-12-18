package com.tarena.lbs.basic.web.service;

import com.tarena.lbs.basic.web.repository.AdminRepository;
import com.tarena.lbs.pojo.passport.param.AdminLoginParam;
import com.tarena.lbs.pojo.passport.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginService {
    // data layer convention:
    // 1. each table to one layer, **Repository, **Mapper
    // 2. in/output output must be PO

    @Autowired
    private AdminRepository adminRepository;
    public LoginVO doLogin(AdminLoginParam param) {
        // use phone in param to lookup admin table
        return null;
    }
}
