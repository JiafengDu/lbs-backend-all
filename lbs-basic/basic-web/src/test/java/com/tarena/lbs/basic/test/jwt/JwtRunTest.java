package com.tarena.lbs.basic.test.jwt;

import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.common.passport.encoder.JwtEncoder;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import org.junit.Before;
import org.junit.Test;

public class JwtRunTest {
     // JWT encoder is used
    private JwtEncoder<AdminPO> jwtEncoder;

    // initialize jwtEncoder. Use @before in test
    // if used in spring, use bean
    @Before
    public void init() {
        jwtEncoder = new JwtEncoder<>();
        // set secret key
        jwtEncoder.setSecret("123456");
    }
    // use AdminPO to generate jwt
    @Test
    public void testJwt() throws BusinessException {
        AdminPO adminPO = new AdminPO();
        adminPO.setId(1);
        adminPO.setAccountPhone("13800138000");
        adminPO.setNickname("admin");
        adminPO.setEmail("admin@tarena.com");
        adminPO.setBusinessId(1);
        adminPO.setAccountStatus(1);
        adminPO.setAccountType(1);
        // generate jwt
        String jwt = jwtEncoder.generateToken(adminPO);
        System.out.println(jwt);
    }

    // use jwt to decode
    @Test
    public void testTokenParsePO() throws BusinessException {
        String jwt = "eyJhbGciOiJIUzUxMiJ9.eyJwYXlsb2FkIjoie1wiYWNjb3VudFBob25lXCI6XCIxMzgwMDEzODAwMFwiLFwiYWNjb3VudFN0YXR1c1wiOjEsXCJhY2NvdW50VHlwZVwiOjEsXCJidXNpbmVzc0lkXCI6MSxcImVtYWlsXCI6XCJhZG1pbkB0YXJlbmEuY29tXCIsXCJpZFwiOjEsXCJuaWNrbmFtZVwiOlwiYWRtaW5cIn0ifQ.RmaCDHf0oRUOkHKQPR7XcNL50G9HzeFw-n2HdU15lJ3VjQh2DPbG12axDQegHSHANTT26HjHR3ZKQiBX8lZBWw";
        // use api to decode
        AdminPO po = jwtEncoder.getLoginFromToken(jwt, AdminPO.class);
        System.out.println(po);
    }
}
