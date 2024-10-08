package com.tarena.lbs.common.content.enums;

import lombok.Getter;

@Getter
public enum ArticleActionEnum {
    LIKE(1, "likeCount","点赞"),
    FAVORITE(2, "favoriteCount","收藏"),
    COMENET(3, "accessCount","评论");

    private Integer code;
    private String msg;
    private String name;

    ArticleActionEnum(Integer code, String msg,String name) {
        this.code = code;
        this.msg = msg;
        this.name=name;
    }

    public static String getMsgByCode(Integer code) {
        for (ArticleActionEnum articleActionEnum : ArticleActionEnum.values()) {
            if (articleActionEnum.getCode().equals(code)) {
                return articleActionEnum.getMsg();
            }
        }
        throw new RuntimeException("未找到对应 code 编码");
    }
    public static String getNameByCode(Integer code){
        for (ArticleActionEnum articleActionEnum : ArticleActionEnum.values()) {
            if (articleActionEnum.getCode().equals(code)) {
                return articleActionEnum.getName();
            }
        }
        throw new RuntimeException("未找到对应 code 名字");
    }


}
