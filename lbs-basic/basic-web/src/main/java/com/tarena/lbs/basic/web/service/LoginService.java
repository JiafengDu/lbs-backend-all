package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.StringUtils;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.basic.web.repository.AdminRepository;
import com.tarena.lbs.common.passport.encoder.JwtEncoder;
import com.tarena.lbs.common.passport.enums.Roles;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import com.tarena.lbs.pojo.passport.param.AdminLoginParam;
import com.tarena.lbs.pojo.passport.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginService {
    //数据层的特点:
    //1. 有一个业务表格 就有一套数据层 **Repository **Mapper
    //2. 读写操作的入参和出参 读操作 的入参是查询条件 出参是PO
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private JwtEncoder jwtEncoder;
    public LoginVO doLogin(AdminLoginParam param) throws BusinessException {
        log.info("后台登录业务处理,入参:{}",param);
        String phone = param.getPhone();
        AdminPO po=adminRepository.getAdminByPhone(phone);//数据层命名 最好通过名称知道业务功能
        Asserts.isTrue(po==null,new BusinessException("-2","该手机号不存在"));
        matchPassword(param.getPassword(),po.getAccountPassword());
        String jwt=generateJwt(po);
        return new LoginVO(jwt,po.getId(),po.getNickname());
    }

    private String generateJwt(AdminPO po) throws BusinessException {
        //在jwt中携带的用户信息 并不是adminPO 而是单独设计的一个用户认证对象UserPrinciple
        //原因是要和数据库表格解耦
        UserPrinciple userPrinciple=new UserPrinciple();
        userPrinciple.setId(po.getId());
        userPrinciple.setNickname(po.getNickname());
        //角色 在后台管理中 有2个角色 admin表格 type=0 平台 type=1 商家
        //在认证对象放的角色是一个枚举Role ADMIN SHOP USER
        userPrinciple.setRole(po.getAccountType()==0? Roles.ADMIN:Roles.SHOP);
        return jwtEncoder.generateToken(userPrinciple);
    }

    private void matchPassword(String password, String accountPassword) throws BusinessException {
        //对于字符串 比对相等的逻辑 就是判断非空之后 equals的结果
        //也可以使用现成的各种工具 StringUtils好几种 nacos apache-common lombok
        boolean equals = StringUtils.equals(password, accountPassword);
        Asserts.isTrue(!equals,new BusinessException("-2","密码不相等"));
    }
}
