package com.join.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author join
 * @Description
 * @date 2022/9/2 21:24
 */
@Configuration
@EnableAsync  //开启线程池
public class ThreadPoolConfig {

    @Bean("taskExecutor")
    public Executor AsyncServiceExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        threadPoolTaskExecutor.setCorePoolSize(5);
        //设置最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(5);
        //配置列队大小
        threadPoolTaskExecutor.setQueueCapacity(Integer.MAX_VALUE);
        //设置线程活跃时间
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        //设置线程默认名称
        threadPoolTaskExecutor.setThreadNamePrefix("文章阅读次数更新线程");
        //等待所有任务结束后再关闭线程
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //初始化线程
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
