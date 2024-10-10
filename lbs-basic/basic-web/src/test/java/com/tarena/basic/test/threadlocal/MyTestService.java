package com.tarena.basic.test.threadlocal;

/**
 * 模拟最终的业务
 */
public class MyTestService {
    public void detail(){
        System.out.println("查询admin详情");
        //线程下游接参
        String userPrinciple = ThreadLocalUtils.get();
        System.out.println("业务下游 拿到 上游数据:"+userPrinciple);
    }
}
