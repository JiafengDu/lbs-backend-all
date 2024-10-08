package com.tarena.lbs.common.passport.enums;

import com.tarena.lbs.base.protocol.enums.ErrorCodeSupport;
import lombok.Getter;

@Getter
public enum PassportResultEnum implements ErrorCodeSupport {
    //200* 验证码业务
    EMPTY_VCODE("2001","验证码不能为空"),
    EMPTY_SESSION_VCODE("2002","请先生成验证码"),
    INVALID_VCODE("2003","验证码不正确" ),
    CREATE_VCODE_FAILED("2004","验证码生成失败，请检查" ),
    //100* 用户登录业务
    USER_NONE_EXIST("1001","用户不存在" ),
    ADMIN_NONE_EXIST("3001","账号不存在" ),
    USER_PASSWORD_ERROR("1002","密码不正确" ),
    TOKEN_GENERATE_FAILED("1003","JWT生成失败，请检查"),
    USER_EXIST("1004","注册用户名已存在" ),
    SYSTEM_ERROR("9999","系统异常，请检查" ),
    BIND_ERROR("9998","参数绑定异常，请检查" ),
    JWT_PARSE_ERROR("-999","解析jwt失败" ),
    GATEWAY_ERROR("3001", "网关错误，请检查是否存在服务"),
    EMPTY_PASSWORD("3002","请填写登录密码" ),
    EMPTY_ACCOUNT_PASSWORD("3003","数据库密码异常" ),
    ADMIN_PASSWORD_ERROR("3004","登录密码不正确" );


    @Override public String getCode() {
        return code;
    }

    @Override public String getMessage() {
        return message;
    }
    PassportResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    private String code;

    private String message;
}
