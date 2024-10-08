package com.tarena.lbs.common.content.enums;

import com.tarena.lbs.base.protocol.enums.ErrorCodeSupport;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContentResultEnum implements ErrorCodeSupport {

    AUTHENTICATION_DATA_ERROR("-2","无法封装认证数据" ),
    SEARCH_ERROR("-2", "查询数据出现异常");
    private String code;
    private String message;
}
