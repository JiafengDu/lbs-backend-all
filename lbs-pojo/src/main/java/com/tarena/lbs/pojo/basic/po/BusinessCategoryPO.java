package com.tarena.lbs.pojo.basic.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("lbs_business_category")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BusinessCategoryPO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("label")
    private String label;

    @TableField("remark")
    private String remark;

    @TableField("status")
    private Integer status;

    @TableField("sort")
    private Integer sort;

    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
}

