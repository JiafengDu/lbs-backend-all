package com.tarena.lbs.pojo.basic.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserParam implements Serializable{

    @ApiModelProperty("用户ID")
    private Integer id;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("用户昵称")
    private String nickName;
    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("地区")
    private String ipAddress;

    @ApiModelProperty("简介")
    private String introduction;
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("渠道来源")
    private String providerType;

    @ApiModelProperty("用户头像ID")
    private String userImage;
}