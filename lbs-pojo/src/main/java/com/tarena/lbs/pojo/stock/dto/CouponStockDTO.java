package com.tarena.lbs.pojo.stock.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CouponStockDTO implements Serializable {

    private Integer id;

    private Integer couponId;

    private Integer businessId;

    private Integer num;

    private Date createAt;

    private Date updateAt;
}