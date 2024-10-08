package com.tarena.lbs.pojo.message.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
@ApiModel(description = "添加的定时消息模板数据")
public class TimingMsgTemplateInsertParam implements Serializable {

    @ApiModelProperty(value = "模板名称",required = true)
    private String tempName;

    @ApiModelProperty(value = "消息标题",required = true)
    private String msgTitle;

    @ApiModelProperty(value = "消息内容",required = true)
    private String content;

    @ApiModelProperty(value = "营销活动id",required = true)
    private Integer activityId;

    @ApiModelProperty(value = "状态 0启用 1禁用",required = true)
    private Integer status;


    private static final long serialVersionUID = 1L;


}