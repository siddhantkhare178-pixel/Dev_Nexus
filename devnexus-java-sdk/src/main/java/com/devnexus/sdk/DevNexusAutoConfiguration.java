package com.devnexus.sdk;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DevNexusProperties.class)
@ConditionalOnProperty(prefix = "devnexus.sdk", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DevNexusAutoConfiguration {

    @Bean
    public DevNexusClient DevNexusClient(DevNexusProperties properties) {
        return new DevNexusClient(properties.getApiKey(), properties.getBaseUrl());
    }

    @Bean
    public DevNexusGlobalExceptionHandler DevNexusGlobalExceptionHandler(DevNexusClient client) {
        return new DevNexusGlobalExceptionHandler(client);
    }
}
