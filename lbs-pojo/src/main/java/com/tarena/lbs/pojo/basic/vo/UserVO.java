package com.tarena.lbs.pojo.basic.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class UserVO implements Serializable {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "地址")
    private String ipAddress;

    @ApiModelProperty(value = "注册时间")
    private Date regTime;

    @ApiModelProperty(value = "最后一次登陆时间")
    private Date lastLogTime;

    @ApiModelProperty(value = "职业")
    private String introduction;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "用户来源")
    private Integer providerType;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "唯一标志")
    private String unionId;

    @ApiModelProperty(value = "头像")
    private String userNickPicture;;

}
