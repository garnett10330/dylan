package com.momo.dylantest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;


@Configuration
public class CustomPrometheusConfig {

    @Bean("customMetricsCommonTags")  // 指定一個唯一的 Bean 名稱
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config()
                .commonTags("application", "dylantest");
    }
}
