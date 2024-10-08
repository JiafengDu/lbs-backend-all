package com.tarena.lbs.pojo.basic.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserLocationParam implements Serializable {

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("维度")
    private String latitude;

    @ApiModelProperty("经度")
    private String longitude;
}