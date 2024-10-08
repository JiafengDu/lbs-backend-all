package com.tarena.lbs.pojo.message.dos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName trigger_msg_template
 */
@Data
@ApiModel("触发消息")
public class TriggerMsgTemplate implements Serializable {
    @ApiModelProperty("编号")
    private Integer id;

    @ApiModelProperty("模板名称")
    private String tempName;

    @ApiModelProperty("用户动作")
    private String userAction;

    @ApiModelProperty("消息标题")
    private String msgTitle;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("状态：0启用、1禁用")
    private Integer status;

    @ApiModelProperty("商家ID")
    private Integer bussinessId;

    @ApiModelProperty("创建时间")
    private Date createAt;
}