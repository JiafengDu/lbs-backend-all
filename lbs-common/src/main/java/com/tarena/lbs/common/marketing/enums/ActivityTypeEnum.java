package com.tarena.lbs.common.marketing.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ActivityTypeEnum {

    GIVE(1, "赠送"),
    TEAMWORK(2, "拼团"),
    CRUSH(3,"秒杀"),
    CASH_BACK(4,"返现"),
    RAFFLE(5,"抽奖"),
    ;


    private Integer code;
    private String message;

}

