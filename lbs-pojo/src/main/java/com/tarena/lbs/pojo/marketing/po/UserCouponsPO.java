package com.tarena.lbs.pojo.marketing.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("user_coupons") // 指定表名
public class UserCouponsPO implements Serializable {

    @TableId(value = "id", type = IdType.AUTO) // 主键注解，指定主键自增
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("coupon_id")
    private Integer couponId;

    @TableField("receive_channel")
    private Integer receiveChannel;

    @TableField("status")
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private Date createAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateAt;

    @TableField("activity_id")
    private Integer activityId;

    @TableField("coupon_code")
    private String couponCode;

    @TableField("order_no")
    private String orderNo;

    @TableField("used_time")
    private Date usedTime;

    @TableField("coupon_type")
    private Integer couponType;

    @TableField("coupon_value")
    private BigDecimal couponValue;

    @TableField("shop_id")
    private Integer shopId;
}
