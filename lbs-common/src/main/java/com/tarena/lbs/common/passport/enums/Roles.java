package com.tarena.lbs.common.passport.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Roles {
    ADMIN(0,"平台管理员"),
    SHOP(1,"商家账号"),
    USER(2,"小程序用户");
    private Integer type;
    private String discription;
    public static Roles getfromType(Integer type) {
        for (Roles role : values()) {
            if (role.getType().equals(type)) {
                return role;
            }
        }
        throw new IllegalArgumentException("不存在对应角色:"+type);
    }
}
