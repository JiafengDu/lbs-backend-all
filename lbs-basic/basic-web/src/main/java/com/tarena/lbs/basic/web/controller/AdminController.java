package com.tarena.lbs.basic.web.controller;

import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.basic.web.service.AdminService;
import com.tarena.lbs.common.passport.encoder.JwtEncoder;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.pojo.basic.vo.AdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/***
 * specifically for backend admin related operations
 */
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtEncoder<UserPrinciple> jwtEncoder;

    @GetMapping("/admin/basic/role/detail")
    public Result<AdminVO> detail(@RequestHeader("Authorization") String jwt) throws BusinessException {
        // 1. get Authorization from header
        // 2. decode jwt token, get userPrinciple id nickname roles
        UserPrinciple userPrinciple = jwtEncoder.getLoginFromToken(jwt, UserPrinciple.class);
        // 3. get adminId from jwt token
        Integer id = userPrinciple.getId();
        AdminVO vo = adminService.detail(id);
        return new Result(vo);
    }
}
