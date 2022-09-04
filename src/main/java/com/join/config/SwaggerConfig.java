package com.join.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;

/**
 * @author join
 * @Description
 * @date 2022/9/2 17:18
 */
@Configuration
//启用swagger
@EnableOpenApi  //与swagger3不同的地方，swagger3使用@EnableOpenApi
public class SwaggerConfig {

    @Bean
    public Docket api() {
//        Docket docket = new Docket(DocumentationType.SWAGGER_2)
//                .host("localhost:8081")// 不配的话，默认当前项目端口
        Docket docket = new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select() // 选择哪些路径和api会生成document
                .apis(RequestHandlerSelectors.any())// 对所有api进行监控
                .apis(RequestHandlerSelectors.basePackage("com.join.controller"))// 选择监控的package
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))// 只监控有ApiOperation注解的接口
                //错误路径不监控
                .paths(Predicate.not(PathSelectors.regex("/error.*")))
                .paths(PathSelectors.regex("/.*"))// 对根下所有路径进行监控
                .build();
        return docket;
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("blog项目接口文档")
                .contact(new Contact("java", "www.myblog.com", "joinhanre@gmail.com"))
                .description("这是用Swagger动态生成的接口文档")
                .termsOfServiceUrl("NO terms of service")
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0")
                .build();
    }

}
