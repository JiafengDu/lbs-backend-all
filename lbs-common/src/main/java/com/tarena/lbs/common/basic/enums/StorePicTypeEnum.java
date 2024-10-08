package com.tarena.lbs.common.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StorePicTypeEnum {
    STORE_LOGO(200, "门店logo"),
    STORE_IMAGE(300, "门店图片")
    ;
    private Integer bizType;
    private String typeDesc;
}
