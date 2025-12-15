package com.natixis.test.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tax Calculator")
                        .version("0.0.1")
                        .description("Documentação da API")
                        .contact(new Contact()
                                .name("Douglas Silva")
                                .email("dcoutinhos@gmail.com")));
    }
}
