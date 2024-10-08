package com.tarena.lbs.attach.web;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "com.tarena.lbs.attach",
    "com.tarena.lbs.*.attach"})
@MapperScan("com.tarena.lbs.attach.web.mapper")
@EnableDubbo
public class AttachWeb {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(AttachWeb.class, args);
    }
}
