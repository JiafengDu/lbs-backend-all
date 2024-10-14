package com.tarena.lbs.basic.web.controller;

import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.service.AdminService;
import com.tarena.lbs.common.passport.encoder.JwtEncoder;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.pojo.basic.query.AdminQuery;
import com.tarena.lbs.pojo.basic.vo.AdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public Result<AdminVO> detail() throws BusinessException {
        AdminVO vo=adminService.detail();
        return new Result(vo);
    }
    //查询后台管理的 账号列表
    @PostMapping("/admin/basic/role/list")
    public Result<PageResult<AdminVO>> pageList(@RequestBody AdminQuery query)
        throws BusinessException{
        PageResult<AdminVO> voPage= adminService.pageList(query);
        return new Result<>(voPage);
    }
}
