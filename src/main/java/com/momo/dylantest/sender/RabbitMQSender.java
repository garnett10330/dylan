package com.momo.dylantest.sender;

import com.momo.dylantest.configProperties.RabbitMQProperties;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private RabbitMQProperties rabbitMQProperties;

    private final String exchange = "normal-exchange";

    // 发送消息到带死信队列的普通队列
    public void sendToNormalQueue(String message) {
        rabbitTemplate.convertAndSend(rabbitMQProperties.getExchangeName(), rabbitMQProperties.getNormalQueue(), message);
        System.out.println("Sent message to normal queue: " + message);
    }

    // 发送消息到无死信队列的普通队列
    public void sendToNoDlqQueue(String message) {
        rabbitTemplate.convertAndSend(rabbitMQProperties.getExchangeName(), rabbitMQProperties.getNoDlqQueue(), message);
        System.out.println("Sent message to no-dlq queue: " + message);
    }
}
