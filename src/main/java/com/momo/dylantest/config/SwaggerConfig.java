package com.momo.dylantest.config;


import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi stocksApi() {
        return GroupedOpenApi.builder()
                .group("stocks")
                .packagesToScan("com.momo.dylantest.controller.fmp") // 替換為你的 Controller 包路徑
                .pathsToMatch("/api/stocks/**")
                .build();
    }
    @Bean
    public GroupedOpenApi messageApi() {
        return GroupedOpenApi.builder()
                .group("message")
                .packagesToScan("com.momo.dylantest.controller.message")
                .pathsToMatch("/api/message/**")
                .build();
    }
    @Bean
    public GroupedOpenApi testApi() {
        return GroupedOpenApi.builder()
                .group("test")
                .packagesToScan("com.momo.dylantest.controller.test")
                .pathsToMatch("/api/**")
                .build();
    }

}

