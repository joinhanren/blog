package com.join.config;

import com.join.interceptor.JWTInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author join
 * @Description
 * @date 2022/8/25 15:24
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Autowired
    private JWTInterceptor jwtInterceptor;
    //跨域配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
    }

    /**
     * 添加JWT拦截器
     * @param registry
     */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(jwtInterceptor)
               .addPathPatterns("/**")
               .excludePathPatterns("/login","/register","/article/list")
               .excludePathPatterns("/sendVerifyCode");

    }
}