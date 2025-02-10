package com.momo.dylantest.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.kafka")
@Data
public class KafkaConfigProperties {
    /**
     * Kafka 叢集位址，例如：localhost:9092
     */
    private String bootstrapServers;

    /**
     * 各個 topic 設定
     */
    private Topics topics;

    /**
     * Consumer 設定
     */
    private Consumer consumer;

    /**
     * Kafka Streams 設定
     */
    private Stream stream;

    @Data
    public static class Topics {
        private String primary;
        private String secondary;
        private String deadLetter;
        private String streamInput;
        private String streamOutput;
    }

    @Data
    public static class Consumer {
        private String groupId;
        private Retry retry;

        @Data
        public static class Retry {
            private long backOffPeriod;
            private int maxAttempts;
        }
    }

    @Data
    public static class Stream {
        private String applicationId;
    }
}
