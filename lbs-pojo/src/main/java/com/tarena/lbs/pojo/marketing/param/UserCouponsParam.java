package com.tarena.lbs.pojo.marketing.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserCouponsParam implements Serializable {
    @ApiModelProperty("用户ID")
    private Integer userId;
    @NotNull(message = "优惠券ID不能为空")
    @ApiModelProperty("优惠券ID")
    private Integer couponId;
    @NotNull(message = "领取渠道不能为空")
    @ApiModelProperty("领取渠道")
    private Integer receiveChannel;
    @NotNull(message = "活动ID不能为空")
    @ApiModelProperty("活动ID")
    private Integer activityId;
    @NotNull(message = "店铺ID不能为空")
    @ApiModelProperty("店铺ID")
    private Integer shopId;
    @ApiModelProperty("优惠券券码-前端忽略")
    private String couponCode;
}
