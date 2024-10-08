package com.tarena.lbs.pojo.message.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
@ApiModel(description = "添加的触发消息模板数据")
public class TriggerMsgTemplateInsertParam implements Serializable {

    @ApiModelProperty(value = "模板名称",required = true)
    private String tempName;


    @ApiModelProperty(value = "用户动作",required = true)
    private String userAction;


    @ApiModelProperty(value = "消息标题",required = true)
    private String msgTitle;

    @ApiModelProperty(value = "消息内容,如${用户名}欢迎注册",required = true)
    private String content;


    @ApiModelProperty(value = "商家ID",required = false)
    private Integer bussinessId;
    private static final long serialVersionUID = 1L;
}