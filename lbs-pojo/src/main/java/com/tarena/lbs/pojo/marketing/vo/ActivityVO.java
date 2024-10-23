package com.tarena.lbs.pojo.marketing.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class ActivityVO {
    @ApiModelProperty(value = "活动ID",example = "1")
    private Integer id;

    @ApiModelProperty(value = "活动名称")
    private String activityName;

    @ApiModelProperty(value = "活动状态0未开始1进行中2结束")
    private Integer status;

    @ApiModelProperty(value = "活动启用状态0禁用1启用2删除")
    private Integer enableStatus;

    @ApiModelProperty(value = "活动类型默认1赠领")
    private Integer activityType;

    @ApiModelProperty(value = "投放渠道1App2小程序")
    private Integer channelType;

    @ApiModelProperty(value = "活动开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;

    @ApiModelProperty(value = "活动结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endDate;

    @ApiModelProperty(value = "活动描述")
    private String describes;

    @ApiModelProperty(value = "活动图片ID")
    private String imgIds;

    @ApiModelProperty(value = "活动图片地址")
    private List<String> imgPics;

    @ApiModelProperty(value = "店铺Ids")
    private String shopIds;

    @ApiModelProperty(value = "目标人群ids")
    private String targetCustomer;

    @ApiModelProperty(value = "活动目的")
    private String activityTarget;

    @ApiModelProperty(value = "投放渠道")
    private Integer deliveryChannel;

    @ApiModelProperty(value = "奖励类型")
    private Integer rewardType;

    @ApiModelProperty(value = "优惠券id")
    private Integer rewardCouponId;

    @ApiModelProperty(value = "分享标题")
    private String shareProfile;

    @ApiModelProperty(value = "分享链接")
    private String shareUrl;

    @ApiModelProperty(value = "营销配置")
    private String activityJson;

    @ApiModelProperty(value = "自动化营销类型")
    private Integer marketingType;

    @ApiModelProperty(value = "商家ID")
    private Integer businessId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt;
}