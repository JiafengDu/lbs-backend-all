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

import javax.servlet.http.HttpServletRequest;

/**
 * 专门处理和后台用户相关的业务接口
 */
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtEncoder<UserPrinciple> jwtEncoder;
    @GetMapping("/admin/basic/role/detail")
    public Result<AdminVO> detail(@RequestHeader("Authorization") String jwt) throws BusinessException {
        //1.从请求头获取Authorization的数据
        System.out.println(jwt);
        //2.解析jwt 获取userPrinciple id nickName Roles
        UserPrinciple userPrinciple = jwtEncoder.getLoginFromToken(jwt, UserPrinciple.class);
        //3.获取登录时 携带的adminId
        Integer id = userPrinciple.getId();
        AdminVO vo=adminService.detail(id);
        return new Result(vo);
    }
}
