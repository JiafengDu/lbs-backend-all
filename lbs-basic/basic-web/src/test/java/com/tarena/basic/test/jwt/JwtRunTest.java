package com.tarena.basic.test.jwt;

import com.tarena.lbs.common.passport.encoder.JwtEncoder;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import org.junit.Before;

public class JwtRunTest {
    //将jwt工具代码封装了一个 编码器
    //编码器的泛型就是你想要使用工具存储的数据实体
    private JwtEncoder<AdminPO> jwtEncoder;

    //对encoder初始化 测试代码使用的Before注解.测试代码运行一次就结束了
    //如果想要在容器spring中使用encoder需要构造encoder的bean对象
    @Before
    public void init() {
        jwtEncoder = new JwtEncoder<>();
        //设置秘钥 密匙就是盐
        jwtEncoder.setSecret("123456");
    }
    //在项目中使用这个工具类 做2个工作
    //1.使用AdminPO对象 生成jwt
    //2.拿到一个jwt 反向解析AdminPO
}
