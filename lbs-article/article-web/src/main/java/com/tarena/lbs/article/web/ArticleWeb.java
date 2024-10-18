package com.tarena.lbs.article.web;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//mapper扫描
@MapperScan("com.tarena.lbs.article.web.mapper")
//开启dubbo
@EnableDubbo
public class ArticleWeb {
    public static void main(String[] args) {
        SpringApplication.run(ArticleWeb.class, args);
    }
}
