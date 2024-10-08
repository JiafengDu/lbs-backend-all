package com.tarena.lbs.pojo.basic.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 后台账号实体类
 */
@Data
@TableName("lbs_admin")
public class AdminPO implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("account_phone")
    private String accountPhone;

    @TableField("account_password")
    private String accountPassword;

    @TableField("nickname")
    private String nickname;

    @TableField("email")
    private String email;

    @TableField("business_id")
    private Integer businessId;

    @TableField("account_status")
    private Integer accountStatus;

    @TableField("account_type")
    private Integer accountType;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
