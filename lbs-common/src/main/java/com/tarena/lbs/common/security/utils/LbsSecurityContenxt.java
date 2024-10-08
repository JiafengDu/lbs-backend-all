package com.tarena.lbs.common.security.utils;

import com.tarena.lbs.common.passport.principle.UserPrinciple;

public class LbsSecurityContenxt {
    private static InheritableThreadLocal<UserPrinciple> loginTokenThreadLocal = new InheritableThreadLocal();

    public LbsSecurityContenxt() {
    }

    public static void bindLoginToken(UserPrinciple loginToken) {
        loginTokenThreadLocal.set(loginToken);
    }

    public static UserPrinciple getLoginToken() {
        return (UserPrinciple)loginTokenThreadLocal.get();
    }

    public static void clearLoginToken() {
        loginTokenThreadLocal.remove();
    }
}
