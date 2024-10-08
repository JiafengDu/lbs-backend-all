package com.tarena.lbs.pojo.basic.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("lbs_user") // 使用MyBatis-Plus的表名注解
public class UserPO implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("phone")
    private String phone;

    @TableField("user_name")
    private String userName;

    @TableField("password")
    private String password;

    @TableField("nick_name")
    private String nickName;

    @TableField("ip_address")
    private String ipAddress;

    @TableField(value = "reg_time", fill = FieldFill.INSERT)
    private Date regTime;

    @TableField(value = "last_log_time", fill = FieldFill.UPDATE)
    private Date lastLogTime;

    @TableField("introduction")
    private String introduction;

    @TableField("provider_type")
    private Integer providerType;

    @TableField("status")
    private Integer status;

    @TableField("union_id")
    private String unionId;

    @TableField("user_image")
    private String userImage;
}
