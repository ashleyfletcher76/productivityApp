package com.europace.todo_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AuthClientConfig {
    @Bean
    RestClient userServiceClient() {
        String baseUrl = System.getenv().getOrDefault("USER_SERVICE_BASE_URL", "http://localhost:8081");
        return RestClient.builder().baseUrl(baseUrl).build();
    }
}
