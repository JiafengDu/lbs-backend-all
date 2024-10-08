package com.tarena.lbs.common.content.enums;

import lombok.Getter;

@Getter
public enum SourceEnum {
    FONT(1, "前台新增的文章"),
    BACKEND(2, "后台新增的文章"),
    ;
    private Integer code;
    private String msg;

    SourceEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
