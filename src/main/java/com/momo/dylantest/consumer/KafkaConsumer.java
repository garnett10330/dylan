package com.momo.dylantest.consumer;

import com.momo.dylantest.properties.KafkaConfigProperties;
import com.momo.dylantest.service.message.RabbitMessageService;
import com.momo.dylantest.util.LogUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final KafkaConfigProperties kafkaConfigProperties;

    @Resource
    private RabbitMessageService messageService;
    @Autowired
    private RabbitMessageService messageService1;


    // 監聽 primary topic
    @KafkaListener(
            topics = "#{@kafkaConfigProperties.topics.primary}",
            groupId = "#{@kafkaConfigProperties.consumer.groupId}"
    )
    public void listenPrimary(String message) {
        log.info(LogUtil.info(LogUtil.GATE_OTHER,"Received message from primary topic: {}", message));
        // 當訊息包含 "error" 時，拋出例外以測試 retry 與死信處理
        if (message != null && message.contains("error")) {
            throw new RuntimeException("Simulated exception for retry");
        }
    }

    // 監聽 secondary topic
    @KafkaListener(
            topics = "#{@kafkaConfigProperties.topics.secondary}",
            groupId = "#{@kafkaConfigProperties.consumer.groupId}"
    )
    public void listenSecondary(String message) {
        log.info(LogUtil.info(LogUtil.GATE_OTHER,"Received message from secondary topic: {}", message));
    }

    // 監聽 dead-letter topic
    @KafkaListener(
            topics = "#{@kafkaConfigProperties.topics.deadLetter}",
            groupId = "#{@kafkaConfigProperties.consumer.groupId}-dlq"
    )
    public void listenDeadLetter(String message) {
        log.info(LogUtil.info(LogUtil.GATE_OTHER,"Received message from dead letter topic: {}", message));
    }
}