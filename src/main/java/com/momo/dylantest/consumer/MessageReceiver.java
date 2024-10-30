package com.momo.dylantest.consumer;


import com.momo.dylantest.configProperties.RabbitMQConfigProperties;
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
@Slf4j
@Service
public class MessageReceiver {
    @Resource
    private RabbitMQConfigProperties rabbitMQConfigProperties;

    // 监听普通队列，手动ACK
    @RabbitListener(queues = "#{rabbitMQConfigProperties.normalQueue}")
    public void receiveNormalQueue(Message message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws Exception {
        try {
            String msg = new String(message.getBody());
            log.info(LogUtil.info(LogUtil.GATE_FRONT,"receiveNormalQueue","消費訊息:"+msg));
            // 故意抛出异常，模拟处理失败
            if ("fail".equals(msg)) {
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
            String msg = new String(message.getBody());
            log.info(LogUtil.info(LogUtil.GATE_FRONT,"receiveNoDlqQueue","消費訊息:"+msg));
            // 处理消息后手动ACK
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error(LogUtil.error("receiveNoDlqQueue:"+new String(message.getBody()),e));
            // 消息处理失败，可以选择重新放回队列，或者直接丢弃
            channel.basicNack(deliveryTag, false, false);
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