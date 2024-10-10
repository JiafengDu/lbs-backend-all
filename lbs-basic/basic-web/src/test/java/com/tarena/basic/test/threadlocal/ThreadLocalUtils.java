package com.tarena.basic.test.threadlocal;

public class ThreadLocalUtils {
    //准备一个注册器 就是ThreadLocal的实现 底层是一个map对象
    private static ThreadLocal<String> Register = new ThreadLocal<>();
    //存数据 register
    public static void save(String userPrinciple){
        Register.set(userPrinciple);
    }
    //取数据 register
    public static String get(){
        return Register.get();
    }
    //清空数据 register
    public static void clear(){
        Register.remove();
    }
}
