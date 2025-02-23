package com.lhg1006.secondhandmarketplaceboot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Secondhand Marketplace API")
                        .version("1.0")
                        .description("Spring Boot 프로젝트의 API 문서")
                        .contact(new Contact().name("lhg1006").email("lhg961006@gmail.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                );
    }
}