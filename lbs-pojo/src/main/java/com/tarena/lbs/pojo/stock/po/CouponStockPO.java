package com.tarena.lbs.pojo.stock.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
@TableName("coupon_stock") // 指定表名
public class CouponStockPO implements Serializable {

    @TableId(value = "id", type = IdType.AUTO) // 主键，自动增长
    private Integer id;

    @TableField("coupon_id") // 映射到数据库字段coupon_id
    private Integer couponId;

    @TableField("business_id") // 映射到数据库字段business_id
    private Integer businessId;

    @TableField("num") // 映射到数据库字段num
    private Integer num;

    @TableField("create_at") // 映射到数据库字段create_at
    private Date createAt;

    @TableField("update_at") // 映射到数据库字段update_at
    private Date updateAt;
}
