package com.tarena.lbs.pojo.message.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 消息实体类
 */
@Data
@TableName("message")
public class MessagePO implements Serializable {

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 活动ID
     */
    @TableField("activity_id")
    private Integer activityId;
    /**
     * 前台后台资源类型1 前台 2 后台
     */
    @TableField("msg_type")
    private Integer msgType;

    /**
     * 模版ID
     */
    @TableField("template_id")
    private Integer templateId;

    /**
     * 定时消息任务编号
     */
    @TableField("timing_msg_task_id")
    private Integer timingMsgTaskId;

    /**
     * 状态：0:定时消息不可读  1:未读  2:已读
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("create_at")
    private Date createAt;

    /**
     * 消息标题
     */
    @TableField("msg_title")
    private String msgTitle;

    /**
     * 内容
     */
    @TableField("content")
    private String content;
}
