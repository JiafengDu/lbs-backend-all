package com.tarena.lbs.common.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AdminTypeEnum {
    ADMIN(0,"平台管理员"),
    SHOP(1,"商家账号");
    private Integer type;
    private String discription;
}
