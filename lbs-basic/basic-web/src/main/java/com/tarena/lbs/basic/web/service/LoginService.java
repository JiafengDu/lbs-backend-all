package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.StringUtils;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.basic.web.repository.AdminRepository;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import com.tarena.lbs.pojo.passport.param.AdminLoginParam;
import com.tarena.lbs.pojo.passport.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Slf4j
public class LoginService {
    // data layer convention:
    // 1. each table to one layer, **Repository, **Mapper
    // 2. in/output output must be PO

    @Autowired
    private AdminRepository adminRepository;
    public LoginVO doLogin(AdminLoginParam param) throws BusinessException {
        // use phone in param to lookup admin table and get the PO
        log.info("admin login with input param", param);
        String phone = param.getPhone();
        AdminPO po = adminRepository.getAdminByPhone(phone);

//        // decision
//        if (po==null) {
//            // business fail, not code issue: businessException
//            // custom exception
//            throw new BusinessException("-2", "phone number doesn't exist");
//        }
        Asserts.isTrue(po==null, new BusinessException("-2", "phone number doesn't exist in admin table"));

        // compare password
        matchPassword(param.getPassword(), po.getAccountPassword());

        //TODO generate jwt

        return new LoginVO();
    }

    private void matchPassword(String password, String accountPassword) {
        // check if empty, then equals
        // we can also use other, like StringUtils
        boolean equals = StringUtils.equals(password, accountPassword);
        Asserts.isTrue(!equals, new BusinessException("-2","password incorrect"));
    }
}
