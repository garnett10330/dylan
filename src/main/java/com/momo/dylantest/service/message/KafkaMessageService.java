package com.momo.dylantest.service.message;

import com.momo.dylantest.producer.KafkaProducer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

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
    private static final int NUMBER_OF_MESSAGES = 50;

    /**
     * 發送消息到 Single topic。
     *
     * @param message 要發送的消息內容。
     * @return 成功發送消息的提示信息。
     */
    public String sendToSingle(String message) {
        CompletableFuture<?>[] futures = new CompletableFuture[NUMBER_OF_MESSAGES];
        for (int i = 1; i <= NUMBER_OF_MESSAGES; i++) {
            String sendMessage = message + i;
            futures[i - 1] = CompletableFuture.runAsync(() -> kafkaProducer.sendToSingle(sendMessage));
        }
        CompletableFuture.allOf(futures).join(); // 等待所有的任務完成
        return NUMBER_OF_MESSAGES+"Sent message to Single topic: " + message;
    }
    /**
     * 測試發送正常訊息到 MultiConsumer topic
     * @param message 要發送的訊息
     * @return 發送結果字串
     */
    public String sendToMultiConsumer(String message) {
        CompletableFuture<?>[] futures = new CompletableFuture[NUMBER_OF_MESSAGES];
        for (int i = 1; i <= NUMBER_OF_MESSAGES; i++) {
            String sendMessage = message + i;
            futures[i - 1] = CompletableFuture.runAsync(() -> kafkaProducer.sendToMultiConsumer(sendMessage));
        }
        CompletableFuture.allOf(futures).join(); // 等待所有的任務完成
        return NUMBER_OF_MESSAGES+"Sent message to MultiConsumer topic: " + message;
    }

    /**
     * 測試發送正常訊息到 MultiGroup topic (one topic, multiple groups)
     * @param message 要發送的訊息
     * @return 發送結果字串
     */
    public String sendToMultiGroup(String message) {
        CompletableFuture<?>[] futures = new CompletableFuture[NUMBER_OF_MESSAGES];
        for (int i = 1; i <= NUMBER_OF_MESSAGES; i++) {
            String sendMessage = message + i;
            futures[i - 1] = CompletableFuture.runAsync(() -> kafkaProducer.sendToMultiGroup(sendMessage));
        }
        CompletableFuture.allOf(futures).join(); // 等待所有的任務完成
        return NUMBER_OF_MESSAGES+"Sent message to MultiGroup topic: " + message;
    }

    /**
     * 測試發送包含 "error" 字串的訊息到 primary topic，
     * 讓 Consumer 拋出例外以觸發 retry 邏輯，重試失敗後訊息會轉送至死信佇列
     * @param message 要發送的訊息，預設值為 "error"
     * @return 發送結果字串
     */
    public String sendToError( String message) {
        CompletableFuture<?>[] futures = new CompletableFuture[NUMBER_OF_MESSAGES];
        for (int i = 1; i <= NUMBER_OF_MESSAGES; i++) {
            String sendMessage = message + i;
            futures[i - 1] = CompletableFuture.runAsync(() -> kafkaProducer.sendToSingle(sendMessage));
        }
        CompletableFuture.allOf(futures).join();
        return NUMBER_OF_MESSAGES+"Sent error message to trigger retry and dead letter handling: " + message;
    }

    /**
     * 測試發送訊息到 Kafka Streams 的輸入 topic，
     * 此訊息將會由 Kafka Streams 處理後轉成大寫並送出到 stream output topic
     * @param message 要發送的訊息
     * @return 發送結果字串
     */
    public String sendToStream( String message) {
        CompletableFuture<?>[] futures = new CompletableFuture[NUMBER_OF_MESSAGES];
        for (int i = 1; i <= NUMBER_OF_MESSAGES; i++) {
            String sendMessage = message + i;
            futures[i - 1] = CompletableFuture.runAsync(() -> kafkaProducer.sendToInputStream(sendMessage));
        }
        CompletableFuture.allOf(futures).join();
        return NUMBER_OF_MESSAGES+"Sent message to stream input topic: " + message;
    }
}
