package com.tarena.lbs.common.marketing.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CouponCodeStatusEnum {

    ENABLE(0, "未分配"),
    DISABLE(1, "已分配"),
    ;


    private Integer code;
    private String message;

}

