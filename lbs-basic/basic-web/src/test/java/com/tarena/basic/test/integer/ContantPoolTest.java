package com.tarena.basic.test.integer;

import org.junit.Test;

public class ContantPoolTest {
    @Test
    public void test(){
        Integer a=100;
        Integer b=100;
        System.out.println(a==b);
        Integer c=new Integer(200);
        Integer d=new Integer(200);
        System.out.println(c==d);
    }
}
