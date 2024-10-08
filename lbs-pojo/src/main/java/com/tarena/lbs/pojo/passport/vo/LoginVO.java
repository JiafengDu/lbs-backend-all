package com.tarena.lbs.pojo.passport.vo;

import java.io.Serializable;
import lombok.Data;

@Data
public class LoginVO implements Serializable {

    private String jwt;

    private Integer id;

    private String nickname;
}
