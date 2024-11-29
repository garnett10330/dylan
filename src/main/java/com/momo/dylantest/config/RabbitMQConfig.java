package com.momo.dylantest.config;

import com.momo.dylantest.properties.RabbitMQConfigProperties;
import com.momo.dylantest.util.LogUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Slf4j
@Configuration
public class RabbitMQConfig {
    @Resource
    private RabbitMQConfigProperties rabbitMQConfigProperties;
    @Bean
    public Queue normalQueue() {
        return QueueBuilder.durable(rabbitMQConfigProperties.getNormalQueue())
                .withArgument("x-dead-letter-exchange", rabbitMQConfigProperties.getDlxExchangeName())  // 指定死信交换机
                .withArgument("x-dead-letter-routing-key", rabbitMQConfigProperties.getDlqQueue())  // 指定死信路由键
                //.withArgument("x-message-ttl", 5000)  // 消息在5秒后过期
                .build();
    }
    // 无死信队列的普通队列
    @Bean
    public Queue noDlqQueue() {
        return QueueBuilder.durable(rabbitMQConfigProperties.getNoDlqQueue()).build();
    }

    // 死信队列
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(rabbitMQConfigProperties.getDlqQueue()).build();
    }

    // 定义普通交换机
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(rabbitMQConfigProperties.getExchangeName());
    }

    // 定义死信交换机
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(rabbitMQConfigProperties.getDlxExchangeName());
    }

    // 绑定普通队列到普通交换机
    @Bean
    public Binding normalBinding(Queue normalQueue, DirectExchange exchange) {
        return BindingBuilder.bind(normalQueue).to(exchange).with(normalQueue.getName());
    }

    // 绑定无死信队列到普通交换机
    @Bean
    public Binding noDlqBinding(Queue noDlqQueue, DirectExchange exchange) {
        return BindingBuilder.bind(noDlqQueue).to(exchange).with(noDlqQueue.getName());
    }

    // 绑定死信队列到死信交换机
    @Bean
    public Binding dlqBinding(Queue deadLetterQueue, DirectExchange dlxExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(dlxExchange).with(deadLetterQueue.getName());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info(LogUtil.info(LogUtil.GATE_OTHER,"ackSuccess","Message sent successfully: " + correlationData));
            } else {
                log.info(LogUtil.info(LogUtil.GATE_OTHER,"ackFail","Message failed to send: " + cause));
            }
        });
        return rabbitTemplate;
    }
}
