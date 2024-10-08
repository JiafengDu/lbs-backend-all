package com.tarena.lbs.pojo.marketing.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ActivityParam implements Serializable {

    @ApiModelProperty("更新必填")
    private Integer id;

    @NotEmpty(message = "活动名称不能为空")
    @ApiModelProperty("活动名称")
    private String activityName;

    @NotEmpty(message = "开始时间不能为空")
    @ApiModelProperty("开始时间")
    private String startDate;

    @NotEmpty(message = "截止时间不能为空")
    @ApiModelProperty("截止时间")
    private String endDate;

    @NotEmpty(message = "活动说明不能为空")
    @ApiModelProperty("活动说明")
    private String describes;

    @NotEmpty(message = "活动图片ID不能为空")
    @ApiModelProperty("活动图片ID逗号分割")
    private String imgIds;

    @NotEmpty(message = "店铺ID不能为空")
    @ApiModelProperty("店铺ID逗号分割")
    private String shopIds;

    @NotEmpty(message = "活动人群ID不能为空")
    @ApiModelProperty("活动人群ID逗号分割")
    private String targetCustomer;

    @NotEmpty(message = "活动目标不能为空")
    @ApiModelProperty("活动目标")
    private String activityTarget;

    @NotNull(message = "投放渠道不能为空")
    @ApiModelProperty("投放渠道")
    private Integer deliveryChannel;

    @NotNull(message = "优惠券ID不能为空")
    @ApiModelProperty("优惠券ID")
    private Integer rewardCouponId;

    @NotEmpty(message = "活动分享标题不能为空")
    @ApiModelProperty("活动分享标题")
    private String shareProfile;

    @NotEmpty(message = "优惠券ID不能为空")
    @ApiModelProperty("活动分享链接")
    private String shareUrl;

    @ApiModelProperty(value = "营销配置")
    private String activityJson;

    @NotNull(message = "自动化营销类型不能为空")
    @ApiModelProperty(value = "自动化营销类型")
    private Integer marketingType;

    @ApiModelProperty("商家ID-前端忽略")
    private Integer businessId;


}
