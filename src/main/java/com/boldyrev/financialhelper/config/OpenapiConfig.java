package com.boldyrev.financialhelper.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenapiConfig {

    @Bean
    public OpenAPI openApi(@Value("${info.app.name}") final String title,
        @Value("${info.app.version}") final String version) {
        Info info = new Info()
            .title(title)
            .description("Financial Management System Service")
            .version(version)
            .contact(new Contact()
                .name("Alexandr Boldyrev")
                .email("alexandrboldyrev08@gmail.com")
            );
        return new OpenAPI().info(info);
    }
}
