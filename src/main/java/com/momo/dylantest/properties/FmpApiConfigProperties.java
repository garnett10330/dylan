package com.momo.dylantest.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "fmp")
public class FmpApiConfigProperties {
    private String apiKey;
    private String baseUrl;
}

