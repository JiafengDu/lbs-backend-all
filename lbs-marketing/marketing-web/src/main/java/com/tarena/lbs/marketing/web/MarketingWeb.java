package com.tarena.lbs.marketing.web;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tarena.lbs.marketing.web.mapper")
@EnableDubbo
public class MarketingWeb {
    public static void main(String[] args) {
        SpringApplication.run(MarketingWeb.class, args);
    }
}
