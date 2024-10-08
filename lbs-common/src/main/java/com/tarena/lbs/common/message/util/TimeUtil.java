package com.tarena.lbs.common.message.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtil {

    /**
     * 获取当前系统时间
     * @return 当前时间的Date对象
     */
    public static Date getCurrentSystemTime() {
        // 创建一个Date对象，它将被初始化为当前系统时间
        Date now = new Date();

        // 返回创建的Date对象
        return now;
    }
    /**
     * 获取当前系统时间并以特定格式返回
     * @return 格式化的时间字符串
     */
    public static String getCurrentFormattedTime() {
        LocalDateTime now = LocalDateTime.now(); // 获取当前系统时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 创建一个格式化器，指定输出格式
        return now.format(formatter); // 使用格式化器将时间转换为字符串
    }
}
