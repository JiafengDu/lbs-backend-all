package com.tarena.lbs.pojo.basic.query;

import com.tarena.lbs.base.protocol.pager.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class UserGroupQuery extends BasePageQuery implements Serializable {

    @ApiModelProperty(value = "人群名称")
    private String name;

    @ApiModelProperty(value = "开始时间")
    private String startDate;

    @ApiModelProperty(value = "截止时间")
    private String endDate;
}
