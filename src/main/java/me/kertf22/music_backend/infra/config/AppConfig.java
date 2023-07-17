package me.kertf22.music_backend.infra.config;

import me.kertf22.music_backend.infra.security.SecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
public class AppConfig {
    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
    // Other configuration methods...
}