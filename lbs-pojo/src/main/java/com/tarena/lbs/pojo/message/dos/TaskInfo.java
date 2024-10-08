package com.tarena.lbs.pojo.message.dos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName xxl_job_info
 */
@Data
@ApiModel("定时任务列表")
public class TaskInfo implements Serializable {
    /**
     * 编号
     */
    @ApiModelProperty("编号")
    private Integer id;

    /**
     * 任务描述
     */
    @ApiModelProperty("任务描述")
    private String jobDesc;

    /**
     * 添加时间
     */
    @ApiModelProperty("添加时间")
    private Date addTime;

    /**
     * 调度配置
     */
    @ApiModelProperty("时间周期配置")
    private String scheduleConf;


    /**
     * 调度状态：0-停止，1-运行
     */
    @ApiModelProperty("调度状态：0-停止，1-运行")
    private Integer triggerStatus;


    private static final long serialVersionUID = 1L;


}