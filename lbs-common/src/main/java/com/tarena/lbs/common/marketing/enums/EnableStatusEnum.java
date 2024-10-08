package com.tarena.lbs.common.marketing.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum EnableStatusEnum {

    ENABLE(1, "启用"),
    DISABLE(0, "未启用"),
    ;


    private Integer code;
    private String message;

}

