package com.tarena.lbs.pojo.marketing.query;

import com.tarena.lbs.base.protocol.pager.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CouponQuery extends BasePageQuery {

    @ApiModelProperty("优惠券类型")
    private Integer couponType;

    @ApiModelProperty("优惠券状态")
    private Integer status;

    @ApiModelProperty("优惠券名称")
    private String couponName;

    @ApiModelProperty("商家id")
    private Integer businessId;
}
