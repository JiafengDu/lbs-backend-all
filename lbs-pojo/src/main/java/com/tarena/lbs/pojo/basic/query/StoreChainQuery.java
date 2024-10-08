package com.tarena.lbs.pojo.basic.query;

import com.tarena.lbs.base.protocol.pager.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class StoreChainQuery extends BasePageQuery implements Serializable {

    @ApiModelProperty(value = "门店名称", name = "storeName", example = "美食分店")
    private String storeName;

    @ApiModelProperty(value = "省份", name = "provinceId", example = "1")
    private Integer provinceId;

    @ApiModelProperty(value = "区域", name = "areaId", example = "1")
    private Integer areaId;

    @ApiModelProperty(value = "市区", name = "cityId", example = "1")
    private Integer cityId;
}
