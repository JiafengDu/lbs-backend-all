package com.tarena.basic.test.threadlocal;

import org.junit.Test;

public class ThreadLocalRun {
    @Test
    public void test(){
        MyTestFilter filter=new MyTestFilter();
        filter.doFilter();
    }
}
