package com.tarena.lbs.pojo.message.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;


@Data
@ApiModel(description = "添加的定时消息任务数据")
public class TimingMsgTaskUpdateParam implements Serializable {
    @ApiModelProperty(value = "定时任务编号",required = true)
    private Integer id;

    @ApiModelProperty(value = "定时任务名称",required = true)
    private String taskName;

    @ApiModelProperty(value = "定时模板编号",required = true)
    private Integer tempId;

    @ApiModelProperty(value = "接收消息的用户列表",required = true)
    private List userIdList;

    @ApiModelProperty(value = "cron表达式",required = true)
    private String cron;
    
    private static final long serialVersionUID = 1L;
}