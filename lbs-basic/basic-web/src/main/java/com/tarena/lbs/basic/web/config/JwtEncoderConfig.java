package com.tarena.lbs.basic.web.config;

import com.tarena.lbs.common.passport.encoder.JwtEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtEncoderConfig {
    @Bean
    public JwtEncoder jwtEncoder() {
        // set secret key which is "tarena 2024;", also pressent in common
        JwtEncoder jwtEncoder = new JwtEncoder();
        jwtEncoder.setSecret("tarena 2024;");
        return jwtEncoder;
    }
}
