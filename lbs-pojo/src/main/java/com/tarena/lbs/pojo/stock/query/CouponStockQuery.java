package com.tarena.lbs.pojo.stock.query;

import com.tarena.lbs.base.protocol.pager.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CouponStockQuery extends BasePageQuery {

    @ApiModelProperty("优惠券id")
    private Integer couponId;

}
