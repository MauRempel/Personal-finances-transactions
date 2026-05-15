package com.MauRempel.personalFinance.budget.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Personal finance API")
                        .version("1.0")
                        .description("REST API for managing personal financial transactions, balance calculation, and filtered transaction queries")
                .contact(new Contact()
                        .name("Mauricio Rempel")
                        .url("https://github.com/MauRempel")
                ));
    }

}
