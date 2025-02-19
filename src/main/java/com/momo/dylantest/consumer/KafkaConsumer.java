package com.momo.dylantest.consumer;

import com.momo.dylantest.properties.KafkaConfigProperties;
import com.momo.dylantest.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private static final String RECEIVED_MESSAGE_FORMAT = "Group: %s, Message: %s";
    private static final String MULTI_CONSUMER_MESSAGE_FORMAT = 
            "Thread: %s, Partition: %d, Offset: %d, Message: %s";
    private static final String SINGLE_CONSUMER = "Single Consumer";
    private static final String MULTI_CONSUMER = "Multi Consumer";
    private static final String MULTI_GROUP_CONSUMER = "Multi-Group Consumer";

    private final KafkaConfigProperties properties;



    @KafkaListener(
        topics = "#{@kafkaConfigProperties.topics.single.name}",
        groupId = "#{@kafkaConfigProperties.topics.single.group}"
    )
    public void listenSingle(String message) {
        String logMessage = String.format(RECEIVED_MESSAGE_FORMAT,
                properties.getTopics().getSingle().getGroup(), message);
        log.info(LogUtil.info(LogUtil.GATE_OTHER, SINGLE_CONSUMER, logMessage));
        processMessage(properties.getTopics().getSingle().getGroup(), message);
    }

    @KafkaListener(
            topics = "#{@kafkaConfigProperties.topics.multiConsumer.name}",
            groupId = "#{@kafkaConfigProperties.topics.multiConsumer.group}",
            containerFactory = "multiConsumerContainerFactory"
    )
    public void listenMultiConsumer(String message, ConsumerRecord<String, String> record) {
        String logMessage = String.format(MULTI_CONSUMER_MESSAGE_FORMAT,
                Thread.currentThread().getName(),
                record.partition(),
                record.offset(),
                record.value());
        log.info(LogUtil.info(LogUtil.GATE_OTHER, MULTI_CONSUMER, logMessage));
        processMessage(properties.getTopics().getMultiConsumer().getGroup(), message);
    }

    @KafkaListener(
            topics = "#{@kafkaConfigProperties.topics.multiGroup.name}",
            groupId = "#{@kafkaConfigProperties.topics.multiGroup.groups[0]}"
    )
    public void listenMultiGroup_Group0(String message) {
        String groupName = properties.getTopics().getMultiGroup().getGroups().get(0);
        String logMessage = String.format(RECEIVED_MESSAGE_FORMAT, groupName, message);
        log.info(LogUtil.info(LogUtil.GATE_OTHER,
                MULTI_GROUP_CONSUMER + "-Group0", logMessage));
        processMessage(groupName, message);
    }

    @KafkaListener(
            topics = "#{@kafkaConfigProperties.topics.multiGroup.name}",
            groupId = "#{@kafkaConfigProperties.topics.multiGroup.groups[1]}"
    )
    public void listenMultiGroup_Group1(String message) {
        String groupName = properties.getTopics().getMultiGroup().getGroups().get(1);
        String logMessage = String.format(RECEIVED_MESSAGE_FORMAT, groupName, message);
        log.info(LogUtil.info(LogUtil.GATE_OTHER,
                MULTI_GROUP_CONSUMER + "-Group1", logMessage));
        processMessage(groupName, message);
    }

    private void processMessage(String consumerType, String message) {
        String logMessage = String.format("處理消息: %s, 內容: %s", consumerType, message);
        log.info(LogUtil.info(LogUtil.GATE_OTHER, "processMessage", logMessage));

        if (message.contains("error")) {
            throw new RuntimeException("模擬錯誤以測試重試機制");
        }
    }
}