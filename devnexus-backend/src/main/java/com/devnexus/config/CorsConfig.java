package com.devnexus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CorsConfig {

    @Value("${NEXT_PUBLIC_APP_URL:http://localhost:3000}")
    private String appUrl;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);

        // Build allowed origins from environment + safe defaults
        List<String> allowedOrigins = new ArrayList<>();
        allowedOrigins.add(appUrl);
        allowedOrigins.add("http://localhost:3000");
        allowedOrigins.add("http://localhost:8080");
        config.setAllowedOrigins(allowedOrigins);

        config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "X-API-Key"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

