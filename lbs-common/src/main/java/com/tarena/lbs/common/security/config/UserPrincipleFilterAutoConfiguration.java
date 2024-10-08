package com.tarena.lbs.common.security.config;

import com.tarena.lbs.common.passport.encoder.JwtEncoder;
import com.tarena.lbs.common.security.filter.UserPrincipleFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class UserPrincipleFilterAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(UserPrincipleFilter.class)
    public UserPrincipleFilter userPrincipleFilter(){
        log.info("正在创建 user principle解析过滤器");
        UserPrincipleFilter userPrincipleFilter=new UserPrincipleFilter();
        return userPrincipleFilter;
    }
}