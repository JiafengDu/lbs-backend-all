package com.tarena.lbs.common.marketing.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ChannelTypeEnum {

    APP(1, "APP"),
    H5(2, "H5"),
    ;


    private Integer code;
    private String message;

}

