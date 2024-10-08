package com.tarena.lbs.pojo.marketing.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CouponParam implements Serializable {

    @ApiModelProperty("更新必填")
    private Integer id;

    @NotEmpty(message = "优惠券名称不能为空")
    @ApiModelProperty("优惠券名称")
    private String couponName;

    @NotNull(message = "优惠券类型不能为空")
    @ApiModelProperty("优惠券类型")
    private Integer couponType;

    @NotNull(message = "优惠券面额不能为空")
    @ApiModelProperty("优惠券面额")
    private BigDecimal discountValue;

    @NotNull(message = "最大抵扣面额不能为空")
    @ApiModelProperty("最大抵扣面额")
    private BigDecimal maxDiscountAmount;

    @NotEmpty(message = "适用范围不能为空")
    @ApiModelProperty("适用范围")
    private String applicable;

    @NotNull(message = "叠加规则不能为空")
    @ApiModelProperty("叠加规则")
    private Integer usageLimit;

    @NotNull(message = "发行量不能为空")
    @ApiModelProperty("发行量")
    private Integer maxUsageLimit;

    @NotEmpty(message = "开始时间不能为空")
    @ApiModelProperty("开始时间")
    private String startDate;

    @NotEmpty(message = "截止时间不能为空")
    @ApiModelProperty("截止时间")
    private String endDate;

    @NotEmpty(message = "优惠券描述不能为空")
    @ApiModelProperty("优惠券描述")
    private String describes;

    @ApiModelProperty("商家ID-前端忽略")
    private Integer businessId;

    @NotNull(message = "互斥规则不能为空")
    @ApiModelProperty("互斥规则")
    private Integer exclusionType;

    @ApiModelProperty("订单满减")
    private BigDecimal orderAmount;

    @NotNull(message = "领取数量不能为空，0表示不限制")
    @ApiModelProperty(value = "领取数量")
    private Integer usageNum;

}
