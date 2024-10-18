package com.tarena.lbs.article.web.utils;

import com.tarena.lbs.common.passport.principle.UserPrinciple;

/**
 * 类名表示 当前管理的是 认证业务相关环境的工具方法
 */
public class AuthenticationContextUtils {
    //threadlocal 静态对象 同一个进程中 只在内存保管一个就够了
    //准备一个注册器 就是ThreadLocal的实现 底层是一个map对象
    private static ThreadLocal<UserPrinciple> Register = new ThreadLocal<>();
    //存数据 register
    public static void save(UserPrinciple userPrinciple){
        Register.set(userPrinciple);
    }
    //取数据 register
    public static UserPrinciple get(){
        return Register.get();
    }
    //清空数据 register
    public static void clear(){
        Register.remove();
    }
}
