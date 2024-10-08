package com.tarena.lbs.pojo.message.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 动态消息字段实体类
 *
 * @TableName dynamic_fields
 */
@Data
@TableName("dynamic_fields")
public class DynamicFieldsPO implements Serializable {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 消息模板ID
     */
    @TableField("temp_id")
    private Integer tempId;

    /**
     * 字段名：例如，用户名字段
     */
    @TableField("field_name")
    private String fieldName;

    /**
     * 字段值：例如，username
     */
    @TableField("field_value")
    private String fieldValue;

    /**
     * 状态：0有效、1无效
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("create_at")
    private Date createAt;
}
