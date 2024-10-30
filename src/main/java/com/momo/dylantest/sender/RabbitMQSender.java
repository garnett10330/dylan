package com.momo.dylantest.sender;

import com.momo.dylantest.configProperties.RabbitMQConfigProperties;
import com.momo.dylantest.util.LogUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class RabbitMQSender {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private RabbitMQConfigProperties rabbitMQConfigProperties;

    private final String exchange = "normal-exchange";

    // 发送消息到带死信队列的普通队列
    public void sendToNormalQueue(String message) {
        rabbitTemplate.convertAndSend(rabbitMQConfigProperties.getExchangeName(), rabbitMQConfigProperties.getNormalQueue(), message);
        log.info(LogUtil.info(LogUtil.GATE_FRONT,"sendToNormalQueue","Sent message to normal queue: " + message));
    }

    // 发送消息到无死信队列的普通队列
    public void sendToNoDlqQueue(String message) {
        rabbitTemplate.convertAndSend(rabbitMQConfigProperties.getExchangeName(), rabbitMQConfigProperties.getNoDlqQueue(), message);
        log.info(LogUtil.info(LogUtil.GATE_FRONT,"sendToNoDlqQueue","Sent message to no-dlq queue: " + message));
    }
}
