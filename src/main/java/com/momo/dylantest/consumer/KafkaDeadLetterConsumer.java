package com.momo.dylantest.consumer;

import com.momo.dylantest.properties.KafkaConfigProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaDeadLetterConsumer {
    private final KafkaConfigProperties kafkaConfigProperties;


    /**
     * 監聽單一 consumer 的 Dead Letter Topic
     * Topic: {single-topic}-DLT
     * Consumer group: 從配置中取得 deadLetter.singleGroup
     */
    @KafkaListener(
            topics = "#{@kafkaConfigProperties.topics.single.name + '-DLT'}",
            groupId = "#{@kafkaConfigProperties.deadLetter.singleGroup}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeSingleDeadLetter(String message) {
        log.info("【DeadLetter - Single】收到死信訊息: {}", message);
        // 此處進行後續處理，例如通知、重新發送或存檔等待人工檢查
    }

    /**
     * 監聽多 consumer (同一 group) 的 Dead Letter Topic
     * Topic: {multi-topic}-DLT
     * Consumer group: 從配置中取得 deadLetter.multiConsumerGroup
     */
    @KafkaListener(
            topics = "#{@kafkaConfigProperties.topics.multiConsumer.name + '-DLT'}",
            groupId = "#{@kafkaConfigProperties.deadLetter.multiConsumerGroup}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeMultiConsumerDeadLetter(String message) {
        log.info("【DeadLetter - MultiConsumer】收到死信訊息: {}", message);
        // 後續處理邏輯，例如重試、通知或存檔
    }

    /**
     * 監聽多 group 消費的 Dead Letter Topic（Group 1）
     * Topic: {multi-group-topic}-DLT
     * Consumer group: deadLetter.multiGroup.groups[0]
     */
    @KafkaListener(
            topics = "#{@kafkaConfigProperties.topics.multiGroup.name + '-DLT'}",
            groupId = "#{@kafkaConfigProperties.deadLetter.multiGroup.groups[0]}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeMultiGroupDeadLetterGroup1(String message) {
        log.info("【DeadLetter - MultiGroup Group 1】收到死信訊息: {}", message);
        // 後續處理邏輯
    }

    /**
     * 監聽多 group 消費的 Dead Letter Topic（Group 2）
     * Topic: {multi-group-topic}-DLT
     * Consumer group: deadLetter.multiGroup.groups[1]
     */
    @KafkaListener(
            topics = "#{@kafkaConfigProperties.topics.multiGroup.name + '-DLT'}",
            groupId = "#{@kafkaConfigProperties.deadLetter.multiGroup.groups[1]}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeMultiGroupDeadLetterGroup2(String message) {
        log.info("【DeadLetter - MultiGroup Group 2】收到死信訊息: {}", message);
        // 後續處理邏輯
    }

    /** properties.getTopics().getStreamInput().getName()
     * 監聽 Kafka Streams 輸入 Topic 的 Dead Letter
     * Topic: input-stream-topic-DLT
     * Consumer group: 從配置中取得 deadLetter.streams.inputGroup
     */
    @KafkaListener(
            topics = "#{@kafkaConfigProperties.topics.streamInput.name +'-DLT'}",
            groupId = "#{@kafkaConfigProperties.deadLetter.streams.inputGroup}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeInputStreamDeadLetter(String message) {
        log.info("【DeadLetter - Input Stream】收到死信訊息: {}", message);
        // 後續處理邏輯
    }

    /**
     * 監聽 Kafka Streams 輸出 Topic 的 Dead Letter
     * Topic: output-stream-topic-DLT
     * Consumer group: 從配置中取得 deadLetter.streams.outputGroup
     */
    @KafkaListener(
            topics = "#{@kafkaConfigProperties.topics.streamOutput.name +'-DLT'}",
            groupId = "#{@kafkaConfigProperties.deadLetter.streams.outputGroup}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeOutputStreamDeadLetter(String message) {
        log.info("【DeadLetter - Output Stream】收到死信訊息: {}", message);
        // 後續處理邏輯
    }



}
