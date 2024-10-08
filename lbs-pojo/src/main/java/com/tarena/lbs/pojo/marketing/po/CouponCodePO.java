package com.tarena.lbs.pojo.marketing.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("coupon_code") // 指定表名
public class CouponCodePO implements Serializable {

    @TableId(value = "id", type = IdType.AUTO) // 主键注解，指定主键自增
    private Integer id;

    @TableField("coupon_id")
    private Integer couponId;

    @TableField("coupon_code")
    private String couponCode;

    @TableField("status")
    private Integer status;

    @TableField("business_id")
    private Integer businessId;

    @TableField(fill = FieldFill.INSERT)
    private Date createAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateAt;
}
