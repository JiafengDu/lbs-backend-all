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
import org.springframework.web.bind.annotation.RestController;

/***
 * specifically for backend admin related operations
 */
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtEncoder<UserPrinciple> jwtEncoder;

    @GetMapping("/admin/basic/role/detail")
    public Result<AdminVO> detail() throws BusinessException {
        AdminVO vo = adminService.detail();
        return new Result(vo);
    }
}
