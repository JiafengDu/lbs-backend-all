package com.tarena.lbs.pojo.basic.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("lbs_user_tags") // 指定对应的数据库表名
public class UserTagsPO implements Serializable {

    @TableId(value = "id", type = IdType.AUTO) // 标识为主键字段
    private Integer id;

    @TableField("user_id") // 映射数据库列名
    private Integer userId;

    @TableField("tag_id")
    private Integer tagId;
}
