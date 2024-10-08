package com.tarena.lbs.common.basic.util;

import java.util.Random;

public class RandomUserName {

    //年龄
    public static final String[] CHINA_MOBILE = {"老","小","大"};
    //姓氏
    public static final String[] CHINA_UNICOM = {"王","周","李","张","刘","陈","黄","杨","吴","徐","许","孙","马"};

    /**
     * 生成用户名
     */
    private static String createName(int op) {
        Random random = new Random();
        String name;
        switch (op) {
            case 0:
                name = CHINA_MOBILE[random.nextInt(CHINA_MOBILE.length)];
                break;
            case 1:
                name = CHINA_UNICOM[random.nextInt(CHINA_UNICOM.length)];
                break;
            default:
                name = "op标志位有误！";
                break;
        }
        return name;
    }

    public static String createRandomName() {
        return createName(0) + createName(1);
    }

    public static void main(String[] args) {
        System.out.println(createRandomName());
    }
}
