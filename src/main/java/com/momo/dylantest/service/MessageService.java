package com.momo.dylantest.service;

import com.momo.dylantest.sender.RabbitMQSender;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class MessageService {
    @Resource
    private RabbitMQSender rabbitMQSender;

    public String sendToNormal(String message) {
        rabbitMQSender.sendToNormalQueue(message);
        return "Message sent to normal queue!";
    }
    public String sendToNoDlq(String message) {
        rabbitMQSender.sendToNoDlqQueue(message);
        return "Message sent to no-dlq queue!";
    }
    public String sendMutiToNoDlq(String message) {
        ExecutorService executor = Executors.newFixedThreadPool(20); // 创建线程池

        for (int i = 1; i <= 100; i++) {
            String sendMessage = message + i;
            executor.submit(() -> {
                rabbitMQSender.sendToNoDlqQueue(sendMessage);
            });
        }

        executor.shutdown(); // 关闭线程池

        return "100 messages sent concurrently to no-dlq queue!";
    }

}
