package com.tarena.lbs.pojo.message.dos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName timing_msg_template
 */
@Data
@ApiModel("定时消息")
public class TimingMsgTemplate implements Serializable {

    @ApiModelProperty("编号")
    private Integer id;

    @ApiModelProperty("消息模版名称")
    private String tempName;

    @ApiModelProperty("消息标题")
    private String msgTitle;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("营销活动id")
    private Integer activityId;

    @ApiModelProperty("营销活动标题")
    private String activityName;

    @ApiModelProperty("状态 0启用 1禁用")
    private Integer status;

    @ApiModelProperty("绑定商家id")
    private Integer bussinessId;

    @ApiModelProperty("创建时间")
    private Date createAt;

    @ApiModelProperty("模版对应人群")
    private String targetCustomer;

    private static final long serialVersionUID = 1L;
}