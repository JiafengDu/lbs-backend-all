package com.tarena.basic.test.jwt;

import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.common.passport.encoder.JwtEncoder;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import org.junit.Before;
import org.junit.Test;

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
    @Test
    public void testGenerateToken() throws Exception {
        //1.准备数据
        AdminPO po = new AdminPO();
        po.setId(1);
        po.setAccountPhone("123456789");
        po.setNickname("tarena");
        po.setEmail("tarena@qq.com");
        po.setBusinessId(1);
        po.setAccountStatus(1);
        po.setAccountType(1);
        //2. 调用工具api
        String jwt = jwtEncoder.generateToken(po);
        System.out.println(jwt);
    }
    //2.拿到一个jwt 反向解析AdminPO
    @Test
    public void testTokenParsePO() throws BusinessException {
        String jwt="eyJhbGciOiJIUzUxMiJ9.eyJwYXlsb2FkIjoie1wiYWNjb3VudFBob25lXCI6XCIxMjM0NTY3ODlcIixcImFjY291bnRTdGF0dXNcIjoxLFwiYWNjb3VudFR5cGVcIjoxLFwiYnVzaW5lc3NJZFwiOjEsXCJlbWFpbFwiOlwidGFyZW5hQHFxLmNvbVwiLFwiaWRcIjoxLFwibmlja25hbWVcIjpcInRhcmVuYVwifSJ9.6XzJ_XVa-YPPq4PRqpQMMn0DkR12-dqFH_Q45DuEuOuor-kQtiL-Rl0uIjwOF7Rz5UddHs2KYmGvBoS8hDy3Uw";
        //调用api 解析
        AdminPO po = jwtEncoder.getLoginFromToken(jwt, AdminPO.class);
        System.out.println(po);
    }







}
