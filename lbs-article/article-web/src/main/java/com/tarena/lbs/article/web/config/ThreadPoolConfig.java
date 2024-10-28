package com.tarena.lbs.article.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 容器中需要使用多线程 可以指定当前配置的自定义线程池
 */
@Configuration
@Slf4j
public class ThreadPoolConfig {
    //创建一个 bean对象 使用线程池
    @Bean("myExecutor")
    public ThreadPoolTaskExecutor asyncExecutor(){
        log.info("正在创建自定义线程池");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //1.核心线程数 10
        executor.setCorePoolSize(10);
        //2.最大线程数 20
        executor.setMaxPoolSize(20);
        //3.队列最大长度 100
        executor.setQueueCapacity(100);
        //4.keepAliveTime 10
        executor.setKeepAliveSeconds(10);
        //5.自定义线程 前缀 线程名称
        executor.setThreadNamePrefix("my-thread-");
        //6.拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
