package com.tarena.lbs.basic.test.jwt;

import com.tarena.lbs.common.passport.encoder.JwtEncoder;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import org.junit.Before;

public class JwtRunTest {
     // JWT encoder is used
    private JwtEncoder<AdminPO> jwtEncoder;

    // initialize jwtEncoder. Use @before in test
    // if used in spring, use bean
    // use AdminPO to generate jwt
    @Before
    public void init() {
        jwtEncoder = new JwtEncoder<>();
        // set secret key
        jwtEncoder.setSecret("123456");
    }

    // use jwt to decode
}
