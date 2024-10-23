package com.tarena.lbs.stock.web;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tarena.lbs.stock.web.mapper")
@EnableDubbo
public class StockWeb {
    public static void main(String[] args) {
        SpringApplication.run(StockWeb.class, args);
    }
}
