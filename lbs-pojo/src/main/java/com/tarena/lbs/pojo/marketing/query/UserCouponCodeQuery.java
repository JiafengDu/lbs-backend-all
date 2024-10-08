package com.tarena.lbs.pojo.marketing.query;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCouponCodeQuery implements Serializable {

    @ApiModelProperty("用户ID")
    private Integer userId;

    @NotNull(message = "优惠券券码不能为空")
    @ApiModelProperty("优惠券券码")
    private String couponCode;
}
