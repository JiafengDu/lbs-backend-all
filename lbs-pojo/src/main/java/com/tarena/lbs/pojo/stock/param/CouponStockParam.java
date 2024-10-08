package com.tarena.lbs.pojo.stock.param;

import java.io.Serializable;
import lombok.Data;

@Data
public class CouponStockParam implements Serializable {

    private Integer couponId;

    private Integer businessId;

    private Integer num;

}
