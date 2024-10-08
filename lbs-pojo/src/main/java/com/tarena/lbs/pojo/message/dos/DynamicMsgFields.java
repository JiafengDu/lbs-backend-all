package com.tarena.lbs.pojo.message.dos;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName dynamic_msg_fields
 */
@Data
public class DynamicMsgFields implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 消息模板ID
     */
    private Integer tempId;

    /**
     * 字段名：例如，用户名字段
     */
    private String fieldName;

    /**
     * 字段值：例如，username
     */
    private String fieldValue;

    /**
     * 状态：0有效、1无效
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createAt;

    private static final long serialVersionUID = 1L;
}