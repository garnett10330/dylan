//package com.momo.dylantest.config;
//
//import org.springdoc.core.models.GroupedOpenApi;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public GroupedOpenApi api() {
//        return GroupedOpenApi.builder()
//                .group("default")
//                .packagesToScan("com.momo.dylantest.controller") // 替換為你的 Controller 包路徑
//                .pathsToMatch("/api/**")
//                .build();
//    }
//
//    @Bean
//    public org.springdoc.core.configuration.SpringDocConfiguration springDocConfiguration() {
//        return new org.springdoc.core.configuration.SpringDocConfiguration();
//    }
//}
//
