package com.tarena.lbs.basic.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tarena.lbs.basic.web.mapper")
public class BasicWeb {
    public static void main(String[] args) {
        SpringApplication.run(BasicWeb.class, args);
    }
}
