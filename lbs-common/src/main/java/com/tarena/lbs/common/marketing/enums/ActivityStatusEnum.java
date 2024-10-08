package com.tarena.lbs.common.marketing.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ActivityStatusEnum {

    ENABLE(1, "启用/进行中"),
    DISABLE(0, "禁用/未开始"),
    DELETED(2,"删除/已过期")
    ;


    private Integer code;
    private String message;

}

