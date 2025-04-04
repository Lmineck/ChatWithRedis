package org.chatwithredis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")        // 어떤 경로에 대해
                .allowedOrigins("*")          // 어떤 출처에서 와도 허용할지
                .allowedMethods("GET", "POST");
    }
}
