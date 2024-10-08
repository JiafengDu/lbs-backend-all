package com.tarena.lbs.common.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusStatusEnum {
   /*
       审核通过状态 2（包括3-4）
       默认是3
    */
    PENDING(1, "审核中"),
    APPROVED(2, "审核通过"),
    REJECTED(3, "审核驳回"),
    ENABLE(4, "分配账号,默认启用状态"),
    DISABLED(5, "账户禁用");
    private Integer code;
    private String message;
}
