package com.momo.dylantest.service.message;

import com.momo.dylantest.producer.RabbitMQProducer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
/**
 * 消息服務類。
 *
 * 此類負責向 RabbitMQ 的不同佇列發送消息。
 * 包含發送到普通佇列、無死信佇列，以及批量發送消息的功能。
 */
@Service
@Slf4j
public class RabbitMessageService {
    @Resource
    private RabbitMQProducer rabbitMQProducer;
    /**
     * 發送消息到普通佇列包含死信。
     *
     * @param message 要發送的消息內容。
     * @return 成功發送消息的提示信息。
     */
    public String sendToNormal(String message) {
        rabbitMQProducer.sendToNormalQueue(message);
        return "Message sent to normal queue!"+message;
    }
    /**
     * 發送消息到無死信佇列。
     *
     * @param message 要發送的消息內容。
     * @return 成功發送消息的提示信息。
     */
    public String sendToNoDlq(String message) {
        rabbitMQProducer.sendToNoDlqQueue(message);
        return "Message sent to no-dlq queue!"+message;
    }
    /**
     * 批量發送 100 條消息到無死信佇列。
     *
     * @param message 要發送的消息內容前綴，每條消息將附加一個唯一的數字後綴。
     * @return 成功發送消息的提示信息。
     */
    public String sendMutiToNoDlq(String message) {
        int numberOfMessages = 5000;
        CompletableFuture<?>[] futures = new CompletableFuture[numberOfMessages];
        for (int i = 1; i <= numberOfMessages; i++) {
            String sendMessage = message + i;
            futures[i - 1] = CompletableFuture.runAsync(() -> rabbitMQProducer.sendToNoDlqQueue(sendMessage));
        }
        CompletableFuture.allOf(futures).join(); // 等待所有的任務完成
        return numberOfMessages+" messages sent concurrently to no-dlq queue!";
    }

}
