package com.tarena.lbs.pojo.message.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户注册后，发给消息模块的数据
 */
@Data
@ApiModel("消息动态字段")
public class DynamicMsgFieldsParam implements Serializable {


    @ApiModelProperty("字段名")
    private String fieldName;

    /**
     * 模版ID
     */
    private Integer tempId;

}
