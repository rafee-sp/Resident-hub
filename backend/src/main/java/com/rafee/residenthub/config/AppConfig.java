package com.rafee.residenthub.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application")
@Data
public class AppConfig {

    private String frontendUrl;
    private String backendUrl;
}
