package com.tarena.lbs.pojo.basic.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
@Data
public class StoreParam implements Serializable {

    private Integer id;
    @ApiModelProperty(value = "商家ID", name = "businessId", example = "1", required = true)
    private Integer businessId;
    @ApiModelProperty(value = "门店名称", name = "storeName", example = "鹏宇足疗店总店", required = true)
    private String storeName;
    @ApiModelProperty(value = "门店电话", name = "storePhone", example = "19941253145", required = true)
    private String storePhone;
    @ApiModelProperty(value = "负责人姓名", name = "storeHeadName", example = "闫鹏宇", required = true)
    private String storeHeadName;
    @ApiModelProperty(value = "负责人电话", name = "storeHeadPhone", example = "19746753145", required = true)
    private String storeHeadPhone;
    @ApiModelProperty(value = "省份", name = "provinceId", example = "1")
    private Integer provinceId;

    @ApiModelProperty(value = "区域", name = "areaId", example = "1")
    private Integer areaId;

    @ApiModelProperty(value = "市区", name = "cityId", example = "1")
    private Integer cityId;
    @ApiModelProperty(value = "详细地址", name = "storeLocation", example = "中鼎大厦B座412", required = true)
    private String storeLocation;
    @ApiModelProperty(value = "门店经度", name = "storeLongitude", example = "45.1234", required = true)
    private String longitude;
    @ApiModelProperty(value = "门店纬度", name = "storeLatitude", example = "497.4567", required = true)
    private String latitude;
    @ApiModelProperty(value = "门店图片ID数组", name = "storeImagesIds", example = "[1,2]", required = true)
    private List<String> storeImagesIds;
    @ApiModelProperty(value = "门店LOGO图片ID", name = "storeLogo", example = "204", required = true)
    private String storeLogo;
    @ApiModelProperty(value = "门店简介", name = "storeIntroduction", example = "老闫正规洗脚店", required = true)
    private String storeIntroduction;
    @ApiModelProperty(value = "排序", name = "sort", example = "1", required = true)
    private Integer sort;
    @ApiModelProperty(value = "营业时间", name = "tradeTime", example = "08：00-24：00", required = true)
    private String tradeTime;
    @ApiModelProperty(value = "门店状态", name = "storeStatus", example = "0", required = true)
    private Integer storeStatus;




}