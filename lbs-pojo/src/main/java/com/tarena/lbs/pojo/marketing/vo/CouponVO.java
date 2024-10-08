package com.tarena.lbs.pojo.marketing.vo;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class CouponVO {
    @ApiModelProperty(value = "优惠券ID")
    private Integer id;

    @ApiModelProperty(value = "优惠券名称")
    private String couponName;

    @ApiModelProperty(value = "优惠券编码")
    private String couponCode;

    @ApiModelProperty(value = "优惠券类型")
    private Integer couponType;

    @ApiModelProperty(value = "抵扣金额")
    private BigDecimal discountValue;

    @ApiModelProperty(value = "最大抵扣金额")
    private BigDecimal maxDiscountAmount;

    @ApiModelProperty(value = "使用范围")
    private String applicable;

    @ApiModelProperty(value = "最大领取次数")
    private Integer usageLimit;

    @ApiModelProperty(value = "单次消费限制张数")
    private Integer maxUsageLimit;

    @ApiModelProperty(value = "开始时间")
    private Date startDate;

    @ApiModelProperty(value = "截止时间")
    private Date endDate;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "描述")
    private String describes;

    @ApiModelProperty(value = "商家ID")
    private Integer businessId;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;

    @ApiModelProperty(value = "领取数量")
    private Integer usageNum;

    @ApiModelProperty(value = "启用状态")
    private Integer enableStatus;

    @ApiModelProperty(value = "互斥类型")
    private Integer exclusionType;

    @ApiModelProperty(value = "订单金额")
    private Long orderAmount;
}