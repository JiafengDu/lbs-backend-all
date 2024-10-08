package com.tarena.lbs.pojo.basic.query;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.tarena.lbs.base.protocol.pager.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class StoreQuery extends BasePageQuery implements Serializable {

    @ApiModelProperty(value = "门店名称", name = "storeName", example = "美食分店")
    private String storeName;
    @ApiModelProperty(value = "负责人电话", name = "storeHeadPhone", example = "29746753145")
    private String storeHeadPhone;
    @ApiModelProperty(value = "省份", name = "provinceId", example = "1")
    private Integer provinceId;

    @ApiModelProperty(value = "区域", name = "areaId", example = "1")
    private Integer areaId;

    @ApiModelProperty(value = "商家ID", name = "businessId", example = "1")
    private Integer businessId;

    @ApiModelProperty(value = "市区", name = "cityId", example = "1")
    private Integer cityId;

    @ApiModelProperty(value = "更新的时间区间的开始时间", name = "startingTime", example = "2023-11-02 17:12:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startingTime;
    @ApiModelProperty(value = "更新的时间区间的结束时间", name = "endTime", example = "2024-01-02 17:12:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @ApiModelProperty(value = "门店状态（是否启用）0 启用  -1禁用", name = "storeStatus", example = "0")
    private Integer storeStatus;
}