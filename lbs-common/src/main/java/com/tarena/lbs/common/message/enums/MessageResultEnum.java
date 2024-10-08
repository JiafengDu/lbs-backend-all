package com.tarena.lbs.common.message.enums;

import com.tarena.lbs.base.protocol.enums.ErrorCodeSupport;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageResultEnum implements ErrorCodeSupport {

    SUCCESS("0", "success"),
    FAILED("500", "服务错误，请稍后"),

    /**
     * 业务异常，code 从10001开始
     */
    DATA_NOT_FIND("10001", "数据不存在"),


    ;


    /**
     * 状态码
     */
    private String code;
    /**
     * 状态描述
     */
    private String message;

}
