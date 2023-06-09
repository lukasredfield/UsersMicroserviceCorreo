package com.correoargentino.services.user.presentation.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = "com.correoargentino.services.user.presentation.configuration")
public class KeycloakConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}