package com.tarena.lbs.pojo.message.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class MessageVO implements Serializable {

    @ApiModelProperty("编号")
    private Integer id;

    @ApiModelProperty("用户编号")
    private Integer userId;

    @ApiModelProperty("定时任务编号")
    private  Integer timingMsgTaskId;

    @ApiModelProperty("活动ID")
    private  Integer activityId;

    @ApiModelProperty("活动图片")
    private  String activityPic;

    @ApiModelProperty("模版ID")
    private  Integer templateId;

    @ApiModelProperty("内容")
    private  String content;

    @ApiModelProperty("标题")
    private  Integer title;

    @ApiModelProperty("消息状态 0:定时消息不可读  1:未读  2:已读")
    private Integer status;

    @ApiModelProperty("消息标题")
    private String msgTitle;

    @ApiModelProperty("创建时间")
    private Date createAt;

    private static final long serialVersionUID = 1L;
}
