package com.momo.dylantest.consumer;


import com.momo.dylantest.properties.RabbitMQConfigProperties;
import com.momo.dylantest.util.LogUtil;
import com.rabbitmq.client.Channel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

@Slf4j
@Service
public class RabbitMQConsumer {
    @Resource
    private RabbitMQConfigProperties rabbitMQConfigProperties;
    private Random random = new SecureRandom();
    // 监听普通队列，手动ACK
    @RabbitListener(queues = "#{rabbitMQConfigProperties.normalQueue}")
    public void receiveNormalQueue(Message message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        try {
            String msg = new String(message.getBody());
            log.info(LogUtil.info(LogUtil.GATE_FRONT,"receiveNormalQueue","消費訊息:"+msg));
            // 故意抛出异常，模拟处理失败
            if(msg.contains("fail")){
                throw new RuntimeException("測試死信對列");
            }
            // 处理消息后手动ACK
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error(LogUtil.error("receiveNormalQueue:"+new String(message.getBody()),e));
            // 消息处理失败，将消息拒绝并发送到死信队列
            channel.basicNack(deliveryTag, false, false);
        }
    }

    // 监听无死信队列的队列，手动ACK
    @RabbitListener(queues = "#{rabbitMQConfigProperties.noDlqQueue}")
    public void receiveNoDlqQueue(Message message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        try {
            int sleepTime = 50 + random.nextInt(251); // 產生 100 到 300 毫秒之間的隨機時間
            Thread.sleep(sleepTime);
            String msg = new String(message.getBody());
            log.info(LogUtil.info(LogUtil.GATE_FRONT,"receiveNoDlqQueue","消費訊息:"+msg));
            // 处理消息后手动ACK
            channel.basicAck(deliveryTag, false);
        } catch (InterruptedException e) {
            // 恢復中斷狀態
            Thread.currentThread().interrupt();
            log.error(LogUtil.error("線程中斷，無法完成消息處理: " + new String(message.getBody()), e));
            try {
                // 可選處理：將消息重新放回隊列
                channel.basicNack(deliveryTag, false, true);
            } catch (IOException ioException) {
                log.error(LogUtil.error("無法將消息重新放回隊列: " + new String(message.getBody()), ioException));
            }
        } catch (Exception e) {
            // 處理其他異常
            log.error(LogUtil.error("消息處理失敗，丟棄消息: " + new String(message.getBody()), e));
            try {
                channel.basicNack(deliveryTag, false, false); // 丟棄消息
            } catch (IOException ioException) {
                log.error(LogUtil.error("丟棄消息失敗: " + new String(message.getBody()), ioException));
            }
        }
    }

    // 监听死信队列
    @RabbitListener(queues = "#{rabbitMQConfigProperties.dlqQueue}")
    public void receiveDeadLetterQueue(Message message, Channel channel,
                                       @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            String msg = new String(message.getBody());
            log.info(LogUtil.info(LogUtil.GATE_FRONT,"receiveDeadLetterQueue","消費死信對列訊息:"+msg));
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error(LogUtil.error("receiveDeadLetterQueue:"+new String(message.getBody()),e));
            // 消息处理失败，可以选择重新放回队列，或者直接丢弃
            channel.basicNack(deliveryTag, false, false);
        }
    }
}