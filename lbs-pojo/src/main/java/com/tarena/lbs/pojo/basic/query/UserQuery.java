package com.tarena.lbs.pojo.basic.query;

import com.tarena.lbs.base.protocol.pager.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class UserQuery extends BasePageQuery implements Serializable {

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "开始时间")
    private String startDate;

    @ApiModelProperty(value = "截止时间")
    private String endDate;
}
