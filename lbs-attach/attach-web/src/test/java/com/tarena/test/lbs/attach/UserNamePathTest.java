package com.tarena.test.lbs.attach;

import org.junit.Test;

public class UserNamePathTest {
    /*
    检查一下${user.name} 路径值
     */
    @Test
    public void testUserNamePath() {
        System.out.println(System.getProperty("user.home"));
    }
}
