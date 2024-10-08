package com.tarena.lbs.pojo.basic.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 标签库实体类
 */
@Data
@Accessors(chain = true) // 支持链式调用
@TableName("lbs_tag_library") // 指定表名
public class TagLibraryPO implements Serializable {

    @TableId(value = "id", type = IdType.AUTO) // 自增主键注解
    private Integer id; // 标签id主键

    @TableField("tag_type")
    private Integer tagType; // 打标类型:0内容文章 1用户

    @TableField("tag_name")
    private String tagName; // 分类/标签名称

    @TableField("tag_parent_id")
    private Integer tagParentId; // 上级标签id

    @TableField("coding")
    private String coding; // 标签编码

    @TableField("status")
    private Integer status; // 标签状态：0 启用 1禁用

    @TableField("usage_count")
    private Integer usageCount; // 使用次数

    @TableField("tag_desc")
    private String tagDesc; // 标签描述

    @TableField("create_at")
    private Date createdAt; // 标签创建时间

    @TableField("update_at")
    private Date updatedAt; // 标签修改时间
}
