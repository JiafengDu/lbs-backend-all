package com.tarena.lbs.marketing.web.config;

import com.tarena.lbs.common.passport.encoder.JwtEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 初始化一个jwt编码器 生成jwt和解析jwt
 */
@Configuration
public class JwtEncoderConfig {
    @Bean
    public JwtEncoder jwtEncoder(){
        //为了配合解析jwt的逻辑 密匙 盐 必须是"tarena2024;"
        JwtEncoder jwtEncoder=new JwtEncoder();
        jwtEncoder.setSecret("tarena2024;");
        return jwtEncoder;
    }
}
