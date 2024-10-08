package com.tarena.lbs.common.basic.enums;


import com.tarena.lbs.base.protocol.enums.ErrorCodeSupport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum BasicResultEnum implements ErrorCodeSupport {
    ADMIN_PHONE_NOTEXISTS("-2","账号不存在"),
    ADMIN_NOTEXISTS("-2", "找不到账号"),

    BIZ_CATEGORY_NOTEXISTS("-2","商家类型不存在" ),
    BIZ_NOTEXISTS("-2","商家不存在" ),

    USER_PHONE_NOTEXISTS("-2","用户不存在"),

    PASSPORT_NOTMATCH("-2","密码错误" ),
    PHONE_DUPLICATE("-2","手机号重复" ),

    AUTHENTICATION_DATA_ERROR("-2","无法封装认证数据" ),

    ROLE_NOT_FOUND("-2","没有对应角色"), ;
    private String code;
    private String message;
}

