package com.munsun.vacation_pay_service.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@OpenAPIDefinition
public class SwaggerConfiguration {
    @Value("${app.version}")
    private String appVersion;
    @Value("${spring.application.name}")
    private String appName;
    @Value("${app.contact}")
    private String contact;

    private static final String APP_DESCRIPTION = " %s API - сервис для вычисления отпускных.";

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public OpenAPI openAPI() {
        final String apiAppName = appName.substring(0, 1).toUpperCase() + appName.substring(1);
        return new OpenAPI()
                .info(new Info()
                        .title(String.format("%s API", appName))
                        .version(appVersion)
                        .description(String.format(APP_DESCRIPTION, apiAppName))
                        .contact(new Contact()
                                    .name("telegram")
                                    .url(contact)
                        )
                );
    }
}