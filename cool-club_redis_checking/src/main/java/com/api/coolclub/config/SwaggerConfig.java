package com.api.coolclub.config;

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

/*
 * @Author Rohan_Sharma
*/

@Configuration
@Profile("!prod")
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(getInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.api.coolclub.controllers"))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo getInfo() {
        return new ApiInfoBuilder()
            .title("Cool-Club : BACKEND API'S")
            .description("API Documentation for Cool-Club backend.")
            .version("1.0")
            .termsOfServiceUrl(null)
            .contact(null)
            .license(null)
            .licenseUrl(null)
            .build();
    }
}
