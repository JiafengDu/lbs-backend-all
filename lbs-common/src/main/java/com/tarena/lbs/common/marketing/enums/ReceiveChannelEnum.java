package com.tarena.lbs.common.marketing.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ReceiveChannelEnum {

    USER_RECEIVE(1, "用户领取"),
    GRANT(0,"系统发放")
    ;


    private Integer code;
    private String message;
}
