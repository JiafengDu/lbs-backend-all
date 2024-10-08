package com.tarena.lbs.common.marketing.enums;


import com.tarena.lbs.base.protocol.enums.ErrorCodeSupport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MarketingResultEnum implements ErrorCodeSupport {

    AUTHENTICATION_DATA_ERROR("-2","无法封装认证数据"),
    ADMIN_NOT_FOUND("-2","找不到商家信息" ),
    USER_COUPONS_NOTEXIST("-2","优惠券不存在" ),

    PIC_BIND_FAIL("-2","图片绑定失败" ),
    PIC_PARAM_EMPTY("-2","图片参数为空" );

    private String code;
    private String message;

}

