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
@TableName("activity") // 指定表名
public class ActivityPO implements Serializable {

    @TableId(value = "id", type = IdType.AUTO) // 主键注解，指定主键自增
    private Integer id; // 注意：数据库字段类型为bigint，所以这里应该使用Long类型

    @TableField("activity_name")
    private String activityName;

    @TableField("start_date")
    private Date startDate;

    @TableField("end_date")
    private Date endDate;

    @TableField("describes")
    private String describes;

    @TableField("img_ids")
    //1,2,3,4,5
    private String imgIds;

    @TableField("shop_ids")
    private String shopIds;

    @TableField("target_customer")
    private String targetCustomer;

    @TableField("activity_target")
    private String activityTarget;

    @TableField("delivery_channel")
    private Integer deliveryChannel;

    @TableField("reward_coupon_id")
    private Integer rewardCouponId; // 注意：数据库字段类型为varchar，所以这里应该使用String类型

    @TableField("share_profile")
    private String shareProfile;

    @TableField("share_url")
    private String shareUrl;

    @TableField("status")
    private Integer status;

    @TableField("business_id")
    private Integer businessId;

    @TableField(fill = FieldFill.INSERT)
    private Date createAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateAt;

    @TableField("reward_type")
    private Integer rewardType;

    @TableField("enable_status")
    private Integer enableStatus;

    @TableField("activity_type")
    private Integer activityType;

    @TableField("channel_type")
    private Integer channelType;

    @TableField("marketing_type")
    private Integer marketingType;

    @TableField("activity_json")
    private String activityJson;
}
