package com.momo.dylantest.consumer;//package com.momo.dylantest.consumer;
//
//import com.momo.dylantest.util.LogUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.support.AmqpHeaders;
//import org.springframework.context.annotation.Bean;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.function.Consumer;
//@Slf4j
//@Component
//public class MessageConsumer {
//
////    @Bean
//    public Consumer<Message<String>> kafkaConsumer1() {
//        return message -> {
//            try {
//                String payload = message.getPayload();
//                MessageHeaders headers = message.getHeaders();
//                String topic = headers.get("kafka_receivedTopic", String.class);
//                Long offset = headers.get("kafka_offset", Long.class);
//
//                // 處理消息的邏輯
//                processKafkaMessage(payload);
//
//                log.info(LogUtil.info(2,"","成功處理來自主題的 Kafka 消息：" + topic + "，偏移量：" + offset));
//            } catch (Exception e) {
//                log.error(LogUtil.error("處理 Kafka 消息時出錯：" + e.getMessage(),e));
//                throw new RuntimeException("處理消息失敗", e);
//            }
//        };
//    }
//
////    @Bean
//    public Consumer<Message<String>> kafkaConsumer2() {
//        return message -> log.info(LogUtil.info(2,"kafkaConsumer2","Received from Kafka Topic 2: " + message.getPayload()));
//    }
//    @ServiceActivator(inputChannel = "test-group.errors")
//    public void error(Message<?> message, @Header("amqp_replyCode") int replyCode) {
//        System.out.println("消息處理失敗，錯誤碼：" + replyCode);
//        // 這裡可以添加自定義的錯誤處理邏輯
//    }
//    @Bean
//    public Consumer<Message<String>> rabbitConsumer1() {
//        return message -> {
//            String payload = message.getPayload();
//            System.out.println("收到消息: " + payload);
//
//            try {
//                // 模擬處理消息
//                processRabbitMessage(payload);
//
//                // 手動確認消息
//                message.getHeaders().get(AmqpHeaders.CHANNEL, org.springframework.amqp.rabbit.connection.ConnectionFactory.class)
//                        .createConnection()
//                        .createChannel(false)
//                        .basicAck((Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG), false);
//            } catch (Exception e) {
//                // 獲取重試次數
//                Integer retryCount = (Integer) message.getHeaders().getOrDefault("x-retry-count", 0);
//                try {
//                    if (retryCount < 2) {
//                        // 重試次數小於2，拒絕消息並重新入隊
//                        retryRabbitmq(message,true);
//                        log.info(LogUtil.info(2,"rabbitConsumer1Error","消息處理失敗，重試次數: " + (retryCount + 1)));
//                    } else {
//                        // 重試次數達到2次，拒絕消息並不重新入隊（將進入死信隊列）
//                        retryRabbitmq(message,false);
//                        log.info(LogUtil.info(2,"rabbitConsumer1Error","消息處理失敗，已轉入死信隊列"));
//                    }
//                } catch (Exception ex) {
//                    throw new RuntimeException(ex);
//                }
//
//            }
//        };
//    }
//    private void retryRabbitmq(Message<String> message,Boolean isRepublishQueue) throws IOException {
//        message.getHeaders().get(AmqpHeaders.CHANNEL, org.springframework.amqp.rabbit.connection.ConnectionFactory.class)
//                .createConnection()
//                .createChannel(false)
//                .basicReject((Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG), isRepublishQueue);
//    }
//    //@Bean
////    public Consumer<Message<String>> rabbitConsumer1() {
////        return message -> {
////            try {
////                String payload = message.getPayload();
////                MessageHeaders headers = message.getHeaders();
////                String messageId = headers.getId() != null ? headers.getId().toString() : "未知";
////
////                // 處理消息的邏輯
////                processRabbitMessage(payload);
////
////                log.info(LogUtil.info(2,"rabbitConsumer1","成功處理 RabbitMQ 消息，消息 ID：" + messageId));
////            } catch (Exception e) {
////                log.error(LogUtil.error("處理 RabbitMQ 消息時出錯：" + e.getMessage(),e));
////                throw new RuntimeException("處理消息失敗", e);
////            }
////        };
////    }
//
//    @Bean
//    public Consumer<Message<String>> rabbitConsumer2() {
//        return message -> log.info(LogUtil.info(2,"rabbitConsumer2","Received from RabbitMQ Queue 2: " + message.getPayload()));
//    }
//
//    private void processKafkaMessage(String message) {
//        // 模擬處理邏輯
//        if (message.contains("error")) {
//            throw new RuntimeException("Kafka 消息處理錯誤");
//        }
//        log.info(LogUtil.info(2,"processKafkaMessage","處理 Kafka 消息：" + message));
//    }
//
//    private void processRabbitMessage(String message) {
//        // 模擬處理邏輯
//        if (message.contains("error")) {
//            throw new RuntimeException("RabbitMQ 消息處理錯誤");
//        }
//        log.info(LogUtil.info(2,"processRabbitMessage","處理 RabbitMQ 消息：" + message));
//    }
//
//    //@Bean
//    public Consumer<Message<String>> dlqConsumer() {
//        return message -> {
//            log.info(LogUtil.info(2,"dlqConsumer","處理死信隊列消息：" + message.getPayload()));
//            String payload = message.getPayload();
//            MessageHeaders headers = message.getHeaders();
//
//            String originalTopic = headers.get("kafka_originalTopic", String.class);
//            Long originalOffset = headers.get("kafka_originalOffset", Long.class);
//            String originalPartition = headers.get("kafka_originalPartition", String.class);
//            String exception = headers.get("kafka_exceptionMessage", String.class);
//            String exceptionStacktrace = headers.get("kafka_exceptionStacktrace", String.class);
//
//            log.info(LogUtil.info(2,"","處理死信隊列消息：" + payload));
//            log.info(LogUtil.info(2,"","原始主題：" + originalTopic));
//            log.info(LogUtil.info(2,"","原始偏移量：" + originalOffset));
//            log.info(LogUtil.info(2,"","原始分區：" + originalPartition));
//            log.info(LogUtil.info(2,"","異常信息：" + exception));
//            log.info(LogUtil.info(2,"","異常堆棧：" + exceptionStacktrace));
//            // 這裡可以添加死信消息的處理邏輯，例如記錄到數據庫或發送通知
//        };
//    }
//
//}
