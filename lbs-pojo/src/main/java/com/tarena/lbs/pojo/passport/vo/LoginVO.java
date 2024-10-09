package com.tarena.lbs.pojo.passport.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO implements Serializable {

    private String jwt;

    private Integer id;

    private String nickname;
}
