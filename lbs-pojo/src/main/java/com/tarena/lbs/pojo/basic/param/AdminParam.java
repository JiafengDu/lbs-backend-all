package com.tarena.lbs.pojo.basic.param;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AdminParam implements Serializable {
    @ApiModelProperty(value = "账号类型(平台、商家)", name = "accountType", example = "0", required = true)
    private Integer accountType;
    @ApiModelProperty(value = "商家ID", name = "businessId", example = "1", required = true)
    private Integer businessId;
    @ApiModelProperty(value = "手机号", name = "accountPhone", example = "19941253145", required = true)
    private String accountPhone;
    @ApiModelProperty(value = "密码", name = "accountPassword", example = "123456", required = true)
    private String accountPassword;
    @ApiModelProperty(value = "用户名称", name = "nickname", example = "闫鹏宇", required = true)
    private String nickname;
    @ApiModelProperty(value = "电子邮箱", name = "email", example = "yanpengyu5@tedu.cn", required = true)
    private String email;
    @ApiModelProperty(value = "账号状态（是否启用）0 启用 -1禁用", name = "accountStatus", example = "1", required = true)
    private Integer accountStatus;
    public AdminParam() {
    }

    public AdminParam(Integer accountType, Integer businessId, String accountPhone, String accountPassword,
        String nickname, String email, Integer accountStatus) {
        this.accountType = accountType;
        this.businessId = businessId;
        this.accountPhone = accountPhone;
        this.accountPassword = accountPassword;
        this.nickname = nickname;
        this.email = email;
        this.accountStatus = accountStatus;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone == null ? null : accountPhone.trim();
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword == null ? null : accountPassword.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }


}