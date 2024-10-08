package com.tarena.lbs.pojo.marketing.query;
import com.tarena.lbs.base.protocol.pager.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class UserCouponQuery extends BasePageQuery {
    @ApiModelProperty("优惠券状态0未使用1已使用2已过期")
    private Integer status;
    @ApiModelProperty("用户ID")
    private Integer userId;
    @ApiModelProperty("订单编号")
    private String orderNo;
    @ApiModelProperty("投放渠道")
    private Integer receiveChannel;
    @ApiModelProperty("优惠券类型")
    private Integer couponType;
}
