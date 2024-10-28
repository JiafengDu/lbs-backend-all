package com.tarena.lbs.marketing.web;

import com.tarena.lbs.marketing.web.source.MarketingInputSource;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@MapperScan("com.tarena.lbs.marketing.web.mapper")
@EnableDubbo
@EnableBinding(value={MarketingInputSource.class})
public class MarketingWeb {
    public static void main(String[] args) {
        SpringApplication.run(MarketingWeb.class, args);
    }
}
