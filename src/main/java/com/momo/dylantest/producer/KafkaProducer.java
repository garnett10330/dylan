package com.momo.dylantest.producer;

import com.momo.dylantest.properties.KafkaConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaConfigProperties kafkaConfigProperties;

    // 發送到 primary topic
    public void sendToPrimary(String key, String message) {
        kafkaTemplate.send(kafkaConfigProperties.getTopics().getPrimary(), key, message);
    }

    // 發送到 secondary topic
    public void sendToSecondary(String key, String message) {
        kafkaTemplate.send(kafkaConfigProperties.getTopics().getSecondary(), key, message);
    }
    // 發送到 Kafka Streams 的輸入 topic
    public void sendToStreamInput(String key, String message) {
        kafkaTemplate.send(kafkaConfigProperties.getTopics().getStreamInput(), key, message);
    }
}