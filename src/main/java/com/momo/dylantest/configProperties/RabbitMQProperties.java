package com.momo.dylantest.configProperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring-rabbitmq-config")
@Data
public class RabbitMQProperties {
    private String normalQueue = "normal-queue";
    private String noDlqQueue = "no-dlq-queue";
    private String dlqQueue = "normal-queue-dlq";
    private String exchangeName = "normal-exchange";
    private String dlxExchangeName = "dlx-exchange";
}