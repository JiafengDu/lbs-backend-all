package com.tarena.lbs.common.stock.enums;


import com.tarena.lbs.base.protocol.enums.ErrorCodeSupport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StockResultEnum implements ErrorCodeSupport {

    SUCCESS("0", "success"),
    FAILED("500", "服务错误，请稍后"),

    /**
     * 业务异常，code 从10001开始
     */
    DATA_NOT_FIND("10001", "参数错误"),
    DATE_ERROR("10002", "日期参数错误"),
    ACTIVITY_NOT_FIND("20001", "活动不存在"),
    COUPON_NOT_FIND("20001", "优惠券不存在"),
    ;



    private String code;
    private String message;

}

