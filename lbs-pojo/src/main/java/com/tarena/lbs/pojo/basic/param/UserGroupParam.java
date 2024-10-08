package com.tarena.lbs.pojo.basic.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class UserGroupParam implements Serializable {

    @ApiModelProperty("人群名")
    private String groupName;

    @ApiModelProperty("预估人数")
    private Integer userNumber;

    @ApiModelProperty("人群标签")
    private String tagIds;


}
