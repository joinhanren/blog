package com.join;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author join
 * @Description
 * @date 2022/8/25 15:16
 */
@SpringBootApplication
@EnableAspectJAutoProxy//开启aop自动代理
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class,args);
    }

}
