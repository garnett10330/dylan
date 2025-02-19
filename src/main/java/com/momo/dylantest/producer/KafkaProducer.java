package com.momo.dylantest.producer;

import com.momo.dylantest.properties.KafkaConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaConfigProperties kafkaConfigProperties;

    /**
     * 發送訊息到指定的 topic（通用方法）
     *
     * @param topic   目標 topic 名稱
     * @param message 訊息內容
     */
    public void sendMessage(String topic, String key, String message) {
        if(ObjectUtils.isEmpty(key)){
            kafkaTemplate.send(topic, message);
        }else{
            kafkaTemplate.send(topic, key, message);
        }

    }

    /**
     * 發送訊息到單一 consumer 的 topic
     *
     * @param message 訊息內容
     */
    public void sendToSingle(String message) {
        String topic = kafkaConfigProperties.getTopics().getSingle().getName();
        sendMessage(topic,"", message);
    }

    /**
     * 發送訊息到多 consumer（同一 group）的 topic
     *
     * @param message 訊息內容
     */
    public void sendToMultiConsumer(String message) {
        String topic = kafkaConfigProperties.getTopics().getMultiConsumer().getName();
        String key = String.valueOf(System.currentTimeMillis());
        sendMessage(topic,key, message);
    }

    /**
     * 發送訊息到多 group 消費的 topic
     *
     * @param message 訊息內容
     */
    public void sendToMultiGroup(String message) {
        String topic = kafkaConfigProperties.getTopics().getMultiGroup().getName();
        String key = String.valueOf(System.currentTimeMillis());
        sendMessage(topic,key, message);
    }

    /**
     * 發送訊息到 Kafka Streams 的輸入 topic
     *
     * @param message 訊息內容
     */
    public void sendToInputStream(String message) {
        String topic = "input-stream-topic";
        String key = String.valueOf(System.currentTimeMillis());
        sendMessage(topic,key, message);
    }

//    /**
//     * 發送訊息到 Kafka Streams 的輸出 topic
//     * <p>
//     * 注意：通常 Kafka Streams 的 output topic 由 Streams 處理後產生，
//     * 此方法僅供測試或特定需求時使用。
//     *
//     * @param message 訊息內容
//     */
//    public void sendToOutputStream(String message) {
//        String topic = "output-stream-topic";
//        sendMessage(topic, message);
//    }
}