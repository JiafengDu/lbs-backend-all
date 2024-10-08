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
 * 文章分类实体类
 */
@Data
@Accessors(chain = true) // 支持链式调用
@TableName("article_category") // 指定表名
public class ArticleCategoryPO implements Serializable {

    @TableId(value = "id", type = IdType.AUTO) // 自增主键注解
    private Integer id; // 文章分类主键ID

    @TableField("category_name")
    private String categoryName; // 分类名称

    @TableField("sort")
    private Integer sort; // 排序

    @TableField("category_status")
    private Integer categoryStatus; // 状态：1==启用 2==禁用

    @TableField("create_time")
    private Date createTime; // 创建时间

    @TableField("update_time")
    private Date updateTime; // 更新时间
}
