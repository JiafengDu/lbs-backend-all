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
@TableName("coupon") // 指定表名
public class CouponPO implements Serializable {

    @TableId(value = "id", type = IdType.AUTO) // 主键注解，指定主键自增
    private Integer id;

    @TableField("coupon_name")
    private String couponName;

    @TableField("coupon_type")
    private Integer couponType;

    @TableField("discount_value")
    private BigDecimal discountValue;

    @TableField("max_discount_amount")
    private BigDecimal maxDiscountAmount;

    @TableField("applicable")
    private String applicable;

    @TableField("usage_limit")
    private Integer usageLimit;

    @TableField("max_usage_limit")
    private Integer maxUsageLimit;

    @TableField("start_date")
    private Date startDate;

    @TableField("end_date")
    private Date endDate;

    @TableField("status")
    private Integer status;

    @TableField("describes")
    private String describes;

    @TableField("business_id")
    private Integer businessId;

    @TableField(fill = FieldFill.INSERT)
    private Date createAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateAt;

    @TableField("usage_num")
    private Integer usageNum;

    @TableField("enable_status")
    private Integer enableStatus;

    @TableField("exclusion_type")
    private Integer exclusionType;

    @TableField("order_amount")
    private BigDecimal orderAmount;
}
