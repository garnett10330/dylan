package com.momo.dylantest.config;

import com.momo.dylantest.properties.KafkaConfigProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * KafkaTopicConfig 用於本地測試時自動建立 Topic。
 * 注意：這些 Bean 僅適用於開發/測試環境，
 *       在生產環境中建議移除此程式碼，並改由專門的運維工具或流程來管理 Topic 的建立。
 */
@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {

    private final KafkaConfigProperties properties;

    // ---------------------- 單一 consumer Topic ----------------------
    /**
     * 單一 consumer 的 Topic
     */
    @Bean
    public NewTopic singleTopic() {
        int partitions = properties.getTopics().getSingle().getConcurrency();
        return TopicBuilder.name(properties.getTopics().getSingle().getName())
                .partitions(partitions)
                .replicas(1)
                .build();
    }

    /**
     * 單一 consumer 的 Dead Letter Topic (原 Topic + "-DLT")
     */
    @Bean
    public NewTopic singleTopicDLT() {
        int partitions = properties.getTopics().getSingle().getConcurrency();
        return TopicBuilder.name(properties.getTopics().getSingle().getName() + "-DLT")
                .partitions(partitions)
                .replicas(1)
                .build();
    }

    // ---------------------- 多 consumer (同一 group) Topic ----------------------
    /**
     * 多個 consumer 共用的 Topic
     * 分區數依照 yml 中設定的 concurrency 數決定
     */
    @Bean
    public NewTopic multiConsumerTopic() {
        int partitions = properties.getTopics().getMultiConsumer().getConcurrency();
        return TopicBuilder.name(properties.getTopics().getMultiConsumer().getName())
                .partitions(partitions)
                .replicas(1)
                .build();
    }

    /**
     * 多個 consumer 共用的 Dead Letter Topic (原 Topic + "-DLT")
     */
    @Bean
    public NewTopic multiConsumerTopicDLT() {
        int partitions = properties.getTopics().getMultiConsumer().getConcurrency();
        return TopicBuilder.name(properties.getTopics().getMultiConsumer().getName() + "-DLT")
                .partitions(partitions)
                .replicas(1)
                .build();
    }

    // ---------------------- 多 group Topic ----------------------
    /**
     * 多 group 消費用的 Topic
     * 此 Topic 由不同 group 分別消費
     */
    @Bean
    public NewTopic multiGroupTopic() {
        int partitions = properties.getTopics().getMultiGroup().getConcurrency();
        return TopicBuilder.name(properties.getTopics().getMultiGroup().getName())
                .partitions(partitions)
                .replicas(1)
                .build();
    }

    /**
     * 多 group 消費用的 Dead Letter Topic (原 Topic + "-DLT")
     */
    @Bean
    public NewTopic multiGroupTopicDLT() {
        int partitions = properties.getTopics().getMultiGroup().getConcurrency();
        return TopicBuilder.name(properties.getTopics().getMultiGroup().getName() + "-DLT")
                .partitions(partitions)
                .replicas(1)
                .build();
    }

    // ---------------------- Kafka Streams Topic ----------------------
    /**
     * Kafka Streams 使用的輸入 Topic
     */
    @Bean
    public NewTopic inputStreamTopic() {
        int partitions = properties.getTopics().getStreamInput().getConcurrency();
        return TopicBuilder.name(properties.getTopics().getStreamInput().getName())
                .partitions(partitions)
                .replicas(1)
                .build();
    }

    /**
     * Kafka Streams 使用的輸入 Dead Letter Topic (原 Topic + "-DLT")
     */
    @Bean
    public NewTopic inputStreamTopicDLT() {
        int partitions = properties.getTopics().getStreamInput().getConcurrency();
        return TopicBuilder.name(properties.getTopics().getStreamInput().getName()+"-DLT")
                .partitions(partitions)
                .replicas(1)
                .build();
    }

    /**
     * Kafka Streams 使用的輸出 Topic
     */
    @Bean
    public NewTopic outputStreamTopic() {
        int partitions = properties.getTopics().getStreamOutput().getConcurrency();
        return TopicBuilder.name(properties.getTopics().getStreamOutput().getName())
                .partitions(partitions)
                .replicas(1)
                .build();
    }

    /**
     * Kafka Streams 使用的輸出 Dead Letter Topic (原 Topic + "-DLT")
     */
    @Bean
    public NewTopic outputStreamTopicDLT() {
        int partitions = properties.getTopics().getStreamOutput().getConcurrency();
        return TopicBuilder.name(properties.getTopics().getStreamOutput().getName()+"-DLT")
                .partitions(partitions)
                .replicas(1)
                .build();
    }
}
