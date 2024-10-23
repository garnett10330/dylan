package com.momo.dylantest.service;

import com.momo.dylantest.sender.RabbitMQSender;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

}
