package com.tarena.lbs.pojo.basic.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@ApiModel("后台用户响应结果")
@Data
public class AdminVO implements Serializable {

    private Integer id;

    @ApiModelProperty(value = "账户类型",example = "1平台2商家")
    private Integer accountType;

    @ApiModelProperty(value = "商家ID")
    private Integer businessId;

    @ApiModelProperty(value = "手机号")
    private String accountPhone;

    @ApiModelProperty(value = "密码")
    private String accountPassword;

    @ApiModelProperty(value = "用户名")
    private String nickname;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "账号状态")
    private Integer accountStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}