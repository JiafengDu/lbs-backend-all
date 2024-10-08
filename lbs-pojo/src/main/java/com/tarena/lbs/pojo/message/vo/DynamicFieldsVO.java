package com.tarena.lbs.pojo.message.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
@ApiModel("动态字段信息")
public class DynamicFieldsVO implements Serializable {
    @ApiModelProperty("id")
    private Integer id;


    @ApiModelProperty("动态字段名称")
    /**
     * 字段名：例如，用户名字段
     */
    private String fieldName;

    @ApiModelProperty("动态字段值")
    /**
     * 字段名：例如，用户名字段
     */
    private String fieldValue;

    private static final long serialVersionUID = 1L;
}