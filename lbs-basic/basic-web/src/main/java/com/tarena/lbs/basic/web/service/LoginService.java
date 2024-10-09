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

@Service
@Slf4j
public class LoginService {
    //数据层的特点:
    //1. 有一个业务表格 就有一套数据层 **Repository **Mapper
    //2. 读写操作的入参和出参 读操作 的入参是查询条件 出参是PO
    @Autowired
    private AdminRepository adminRepository;
    public LoginVO doLogin(AdminLoginParam param) throws BusinessException {
        //1.利用param中的phone查询是否存在admin数据
        log.info("后台登录业务处理,入参:{}",param);
        String phone = param.getPhone();
        AdminPO po=adminRepository.getAdminByPhone(phone);//数据层命名 最好通过名称知道业务功能
        //2.判断
        //电话号码
        //业务异常 在当前项目使用BusinessException 有2个属性 code和message
        //希望自定义的业务异常的属性 能和  Result匹配(全局异常捕获 将业务异常信息 转化封装Result)
        //自定义断言Asserts 代替 if判断抛异常
        Asserts.isTrue(po==null,new BusinessException("-2","该手机号不存在"));
        //3.对比密码 param中提交密码和po查询的数据库密码
        matchPassword(param.getPassword(),po.getAccountPassword());
        //TODO 4.phone存在 密码相等匹配 我要生成jwt
        return new LoginVO();
    }

    private void matchPassword(String password, String accountPassword) throws BusinessException {
        //对于字符串 比对相等的逻辑 就是判断非空之后 equals的结果
        //也可以使用现成的各种工具 StringUtils好几种 nacos apache-common lombok
        boolean equals = StringUtils.equals(password, accountPassword);
        Asserts.isTrue(!equals,new BusinessException("-2","密码不相等"));
    }
}
