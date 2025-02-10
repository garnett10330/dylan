package com.momo.dylantest.config;

import com.momo.dylantest.properties.KafkaConfigProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * KafkaTopicConfig 用於本地測試時自動建立 Topic。
 * 注意：這些 Bean 僅適用於開發/測試環境，
 *       在生產環境中建議移除此程式碼，並改由專門的運維工具或流程來管理 Topic 的建立。
 */
@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {

    private final KafkaConfigProperties kafkaConfigProperties;

    /**
     * 建立 primary topic。
     *
     * @return NewTopic 物件，設定 partition 數量為 1，複本數為 1
     */
    @Bean
    public NewTopic primaryTopic() {
        // 測試用：自動建立 primary topic
        return new NewTopic(kafkaConfigProperties.getTopics().getPrimary(), 1, (short) 1);
    }

    /**
     * 建立 secondary topic。
     *
     * @return NewTopic 物件，設定 partition 數量為 1，複本數為 1
     */
    @Bean
    public NewTopic secondaryTopic() {
        // 測試用：自動建立 secondary topic
        return new NewTopic(kafkaConfigProperties.getTopics().getSecondary(), 1, (short) 1);
    }

    /**
     * 建立 dead-letter topic。
     *
     * @return NewTopic 物件，設定 partition 數量為 1，複本數為 1
     */
    @Bean
    public NewTopic deadLetterTopic() {
        // 測試用：自動建立 dead-letter topic
        return new NewTopic(kafkaConfigProperties.getTopics().getDeadLetter(), 1, (short) 1);
    }

    /**
     * 建立 Kafka Streams 的輸入 topic。
     *
     * @return NewTopic 物件，設定 partition 數量為 1，複本數為 1
     */
    @Bean
    public NewTopic streamInputTopic() {
        // 測試用：自動建立 stream input topic
        return new NewTopic(kafkaConfigProperties.getTopics().getStreamInput(), 1, (short) 1);
    }

    /**
     * 建立 Kafka Streams 的輸出 topic。
     *
     * @return NewTopic 物件，設定 partition 數量為 1，複本數為 1
     */
    @Bean
    public NewTopic streamOutputTopic() {
        // 測試用：自動建立 stream output topic
        return new NewTopic(kafkaConfigProperties.getTopics().getStreamOutput(), 1, (short) 1);
    }
}
