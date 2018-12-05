package com.qbk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Swagger2 配置
 * Spring中的@Profile注解，可以实现不同环境（开发、测试、部署等）使用不同的配置。可以多个
 */
@Profile({ "dev"})
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 项目标题
     */
    @Value("${info.build.name}")
    private String title ;

    /**
     * 项目描述
     */
    @Value("${info.build.description}")
    private String description ;

    /**
     * 项目版本
     */
    @Value("${info.build.version}")
    private String version ;

    /**
     * 接口路径
     */
    @Value("com.qbk.controller")
    private String basePackage ;

    /**
     * Swagger api docket
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).
                useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.regex("^(?!auth).*$"))
                .build().apiInfo(apiInfo());


    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(title)
                .description(description)
                .version(version)
                .build();
    }


}