package com.tarena.lbs.basic.web.utils;

import com.tarena.lbs.common.passport.principle.UserPrinciple;

public class AuthenticationContextUtils {

    private static final ThreadLocal<UserPrinciple> Register = new ThreadLocal<>();
    public static void setPrinciple(UserPrinciple userPrinciple){
        Register.set(userPrinciple);
    }
    public static UserPrinciple getPrinciple(){
        return Register.get();
    }
    public static void clearPrinciple() {
        Register.remove();
    }

}
