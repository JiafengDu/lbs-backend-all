package com.tarena.lbs.pojo.marketing.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class CouponCodeParam implements Serializable {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("优惠券编码")
    private String couponCode;

    @ApiModelProperty("优惠券id")
    private Integer couponId;

    @ApiModelProperty("商家ID")
    private Integer businessId;

    @ApiModelProperty("状态")
    private Integer status;

}
