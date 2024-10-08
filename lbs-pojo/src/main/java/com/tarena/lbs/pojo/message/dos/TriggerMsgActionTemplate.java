package com.tarena.lbs.pojo.message.dos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName trigger_msg_template
 */
@Data
@ApiModel("行为触发")
public class TriggerMsgActionTemplate implements Serializable {

    @ApiModelProperty("模板名称")
    private String tempName;

    @ApiModelProperty("触发行为")
    private String userAction;

    @ApiModelProperty("消息标题")
    private String msgTitle;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("送达数")
    private Integer count;

    @ApiModelProperty("点击数")
    private Integer viewCount;
}