package com.tarena.lbs.pojo.basic.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 省市区实体类
 */
@Data
@Accessors(chain = true) // 支持链式调用
@TableName("lbs_area") // 指定表名
public class AreaPO implements Serializable {

    @TableId(value = "id", type = IdType.AUTO) // 自增主键注解
    private Long id; // 主键id

    @TableField("parent_id")
    private Long parentId; // 父id

    @TableField("ad_code")
    private String adCode; // 地址编码

    @TableField("city_code")
    private String cityCode; // 城市编码

    @TableField("name")
    private String name; // 省/直辖市/市/区名称

    @TableField("depth")
    private Integer depth; // 层级深度，1:省2:市3:区

    @TableField("is_delete")
    private Integer isDelete; // 删除标记，0:未删除1:已删除

    @TableField("is_municipality")
    private Integer isMunicipality; // 是否是直辖市，1:直辖市，0：其他
}
