package com.tarena.basic.test.threadlocal;

/**
 * 模拟线程上游 过滤器
 */
public class MyTestFilter {
    private MyTestAdapter adapter=new MyTestAdapter();
    //过滤器有过滤器方法
    public void doFilter(){
        //解析了一个jwt
        String userPrinciple="nihao nihao";
        System.out.println("执行过滤逻辑");
        //使用ThreadLocal 存数据
        ThreadLocalUtils.save(userPrinciple);
        //过滤器执行完毕 适配器执行
        adapter.adapt();
    }
}
