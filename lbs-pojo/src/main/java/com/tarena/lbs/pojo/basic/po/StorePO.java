package com.tarena.lbs.pojo.basic.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("lbs_store") // 指定表名
@NoArgsConstructor
@AllArgsConstructor
public class StorePO implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id; // 自增ID

    @TableField("business_id")
    private Integer businessId; // 商家id

    @TableField("store_name")
    private String storeName; // 门店名称

    @TableField("store_phone")
    private String storePhone; // 门店电话

    @TableField("store_head_name")
    private String storeHeadName; // 负责人姓名

    @TableField("store_head_phone")
    private String storeHeadPhone; // 负责人电话

    @TableField("store_location")
    private String storeLocation; // 详细地址

    @TableField("store_longitude")
    private String storeLongitude; // 门店经度

    @TableField("store_latitude")
    private String storeLatitude; // 门店纬度

    @TableField("store_images_id")
    private String storeImagesId; // 门店图片id

    @TableField("store_logo")
    private String storeLogo; // 门店logo

    @TableField("store_introduction")
    private String storeIntroduction; // 门店简介

    @TableField("sort")
    private Integer sort; // 排序

    @TableField("trade_time")
    private String tradeTime; // 营业时间

    @TableField("store_status")
    private Integer storeStatus; // 门店状态（是否启用）0 启用  -1禁用

    @TableField("create_time")
    private Date createTime; // 创建时间

    @TableField("update_time")
    private Date updateTime; // 更新时间

    @TableField("province_id")
    private Integer provinceId; // 所属省级名称

    @TableField("city_id")
    private Integer cityId; // 所属市级名称

    @TableField("area_id")
    private Integer areaId; // 所属区级名称

}
