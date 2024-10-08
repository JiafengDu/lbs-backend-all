package com.tarena.lbs.pojo.marketing.query;

import com.tarena.lbs.base.protocol.pager.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActivityQuery extends BasePageQuery {

    @ApiModelProperty("活动标题")
    private String activityName;

    @ApiModelProperty("投放渠道")
    private Integer deliveryChannel;

    @ApiModelProperty("活动类型")
    private Integer type;

    @ApiModelProperty("活动渠道")
    private Integer channelType;

    @ApiModelProperty("活动状态")
    private Integer status;

    @ApiModelProperty("活动开始时间")
    private String startDate;

    @ApiModelProperty("活动结束时间")
    private String endDate;

    @ApiModelProperty("商家ID-前端忽略")
    private Integer businessId;
}
