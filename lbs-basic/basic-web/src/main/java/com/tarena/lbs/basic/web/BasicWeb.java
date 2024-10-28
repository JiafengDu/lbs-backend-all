package com.tarena.lbs.basic.web;

import com.tarena.lbs.basic.web.source.BasicOutputSource;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@MapperScan("com.tarena.lbs.basic.web.mapper")
@EnableDubbo
@EnableBinding(value = {BasicOutputSource.class})
public class BasicWeb {
    public static void main(String[] args) {
        SpringApplication.run(BasicWeb.class, args);
    }
}
