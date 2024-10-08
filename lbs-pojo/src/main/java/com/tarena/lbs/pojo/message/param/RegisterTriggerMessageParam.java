package com.tarena.lbs.pojo.message.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户注册后，发给消息模块的数据
 */
@Data
@ApiModel("用户触发消息")
public class RegisterTriggerMessageParam extends TriggerMessageParam implements Serializable {

    private  String userAction;

    @ApiModelProperty("用户编号,消息模块通过websocket往用户编号推送消息")
    /**
     * 用户编号,消息模块通过websocket往用户编号推送消息
     */
    private String userId;


    @ApiModelProperty("用户名")

    /**
     * 用户名
     */
    private String username;

    /**
     * 活动ID
     */
    @ApiModelProperty("活动ID")
    private Integer activityId;

    /**
     * 文章ID
     */
    @ApiModelProperty("文章ID")
    private String articleId;

    /**
     * 文章标题
     */
    @ApiModelProperty("文章标题")
    private String articleTitle;



}
