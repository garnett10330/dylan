package com.momo.dylantest.service.message;

import com.momo.dylantest.producer.KafkaProducer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 消息服務類。
 *
 * 此類負責向 kafka 的不同佇列發送消息。
 * 包含發送到普通佇列、無死信佇列，以及批量發送消息的功能。
 */
@Service
@Slf4j
public class KafkaMessageService {
    @Resource
    private KafkaProducer kafkaProducer;

    /**
     * 發送消息到 primary topic。
     *
     * @param message 要發送的消息內容。
     * @return 成功發送消息的提示信息。
     */
    public String sendToPrimary(String message) {
        kafkaProducer.sendToPrimary("key-primary", message);
        return "Sent message to primary topic: " + message;
    }
    /**
     * 測試發送正常訊息到 secondary topic
     * @param message 要發送的訊息
     * @return 發送結果字串
     */
    public String sendToSecondary(String message) {
        kafkaProducer.sendToSecondary("key-secondary", message);
        return "Sent message to secondary topic: " + message;
    }

    /**
     * 測試發送包含 "error" 字串的訊息到 primary topic，
     * 讓 Consumer 拋出例外以觸發 retry 邏輯，重試失敗後訊息會轉送至死信佇列
     * @param message 要發送的訊息，預設值為 "error"
     * @return 發送結果字串
     */
    public String sendToError( String message) {
        kafkaProducer.sendToPrimary("key-error", message);
        return "Sent error message to primary topic to trigger retry and dead letter handling: " + message;
    }

    /**
     * 測試發送訊息到 Kafka Streams 的輸入 topic，
     * 此訊息將會由 Kafka Streams 處理後轉成大寫並送出到 stream output topic
     * @param message 要發送的訊息
     * @return 發送結果字串
     */
    public String sendToStream( String message) {
        kafkaProducer.sendToStreamInput("key-stream", message);
        return "Sent message to stream input topic: " + message;
    }
}
