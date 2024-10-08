package com.tarena.lbs.pojo.passport.param;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminLoginParam implements Serializable {
    @NotBlank(message = "手机号不能为空")
    private String phone;
    @NotBlank
    @NotBlank(message = "密码不能为空")
    private String password;
}
