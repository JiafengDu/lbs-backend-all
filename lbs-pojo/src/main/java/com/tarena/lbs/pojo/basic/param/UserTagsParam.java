package com.tarena.lbs.pojo.basic.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class UserTagsParam implements Serializable {

    @ApiModelProperty("用户id：前台忽略参数")
    private Integer userId;

    @ApiModelProperty("标签id逗号分割")
    private String tagIds;
}