package com.momo.dylantest.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "spring.kafka")
@Data
public class KafkaConfigProperties {
    /**
     * Kafka broker 位址
     */
    private String bootstrapServers;

    /**
     * Consumer 相關自訂設定（重試與 offset 設定）
     */
    private Consumer consumer = new Consumer();

    /**
     * Producer 相關自訂設定
     */
    private Producer producer = new Producer();

    /**
     * Kafka Streams 相關設定
     */
    private Streams streams = new Streams();

    /**
     * Topics 自訂配置（包含單一 consumer、multi-consumer 與多 group 消費）
     */
    private Topics topics = new Topics();

    /**
     * Dead Letter 相關設定：
     * 為各個 Dead Letter Topic 指定對應的 consumer group
     */
    private DeadLetter deadLetter = new DeadLetter();

    @Data
    public static class Consumer {
        /**
         * offset reset 策略，例如 earliest、latest
         */
        private String autoOffsetReset;
        /**
         * 每次重試等待的毫秒數
         */
        private long retryBackoffMs;
        /**
         * 最大嘗試次數（注意：第一次嘗試外，重試次數 = maxAttempts - 1）
         */
        private int maxAttempts;
    }

    @Data
    public static class Producer {
        /**
         * 生產者重試次數
         */
        private int retries;
    }

    @Data
    public static class Streams {
        /**
         * Kafka Streams 的 application id
         */
        private String applicationId;
    }

    @Data
    public static class Topics {
        /**
         * 單一 consumer 範例：一個 topic 對應一個 group、一個 consumer
         */
        private TopicDetail single = new TopicDetail();
        /**
         * 多個 consumer 範例：一個 topic 對應同一 group，但使用多個 consumer（透過 concurrency 設定）
         */
        private TopicDetail multiConsumer = new TopicDetail();
        /**
         * 多個 group 範例：同一 topic 被多個 group 消費
         */
        private MultiGroup multiGroup = new MultiGroup();

        private TopicDetail streamInput = new TopicDetail();

        private TopicDetail streamOutput = new TopicDetail();
    }

    @Data
    public static class TopicDetail {
        /**
         * topic 名稱
         */
        private String name;
        /**
         * consumer group id
         */
        private String group;
        /**
         * 若要使用多個 consumer 實例時的併發數（預設 1）
         */
        private int concurrency = 1;
    }

    @Data
    public static class MultiGroup {
        /**
         * topic 名稱
         */
        private String name;
        /**
         * 若要使用多個 consumer 實例時的併發數（預設 1）
         */
        private int concurrency = 1;
        /**
         * 多個 consumer group id（例如 2–3 個）
         */
        private List<String> groups  = new ArrayList<>();
    }
    @Data
    public static class DeadLetter {
        /**
         * 單一 consumer 對應的 dead letter group
         */
        private String singleGroup;
        /**
         * 多 consumer (同一 group) 對應的 dead letter group
         */
        private String multiConsumerGroup;
        /**
         * 多 group 消費的 dead letter group 設定（可設定多個）
         */
        private MultiGroupDeadLetter multiGroup = new MultiGroupDeadLetter();
        /**
         * Kafka Streams dead letter consumer group 設定
         */
        private StreamsDeadLetter streams = new StreamsDeadLetter();
    }

    @Data
    public static class MultiGroupDeadLetter {
        private List<String> groups  = new ArrayList<>();
    }

    @Data
    public static class StreamsDeadLetter {
        private String inputGroup;
        private String outputGroup;
    }
}