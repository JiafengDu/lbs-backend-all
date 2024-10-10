package com.tarena.basic.test.threadlocal;

/**
 * 中程调用的适配器
 */
public class MyTestAdapter {
    private MyTestService service=new MyTestService();
    //适配器的核心方法 适配
    public void adapt(){
        System.out.println("适配正常执行");
        //调用业务
        service.detail();
    }
}
