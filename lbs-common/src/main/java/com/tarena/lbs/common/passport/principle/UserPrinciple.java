package com.tarena.lbs.common.passport.principle;

import com.tarena.lbs.common.passport.enums.Roles;
import java.io.Serializable;
import lombok.Data;

@Data
public class UserPrinciple implements Serializable {
    private Integer id;
    private String nickname;
    private Roles role;
}
