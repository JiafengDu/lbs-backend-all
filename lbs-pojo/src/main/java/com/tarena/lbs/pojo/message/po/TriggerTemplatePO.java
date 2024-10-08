package com.tarena.lbs.pojo.message.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 触发消息模板实体类
 *
 * @TableName trigger_template
 */
@Data
@TableName("trigger_template")
public class TriggerTemplatePO implements Serializable {

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 模板名称
     */
    @TableField("temp_name")
    private String tempName;

    /**
     * 用户动作
     */
    @TableField("user_action")
    private String userAction;

    /**
     * 消息标题
     */
    @TableField("msg_title")
    private String msgTitle;

    /**
     * 消息内容
     */
    @TableField("content")
    private String content;

    /**
     * 状态：0启用、1禁用
     */
    @TableField("status")
    private Integer status;

    /**
     * 商家ID
     */
    @TableField("bussiness_id")
    private Integer bussinessId;

    /**
     * 创建时间
     */
    @TableField("create_at")
    private Date createAt;
}
