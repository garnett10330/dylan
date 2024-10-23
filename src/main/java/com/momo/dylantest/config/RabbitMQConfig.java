package com.momo.dylantest.config;

import com.momo.dylantest.configProperties.RabbitMQProperties;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Resource
    private RabbitMQProperties rabbitMQProperties;
    @Bean
    public Queue normalQueue() {
        return QueueBuilder.durable(rabbitMQProperties.getNormalQueue())
                .withArgument("x-dead-letter-exchange", rabbitMQProperties.getDlxExchangeName())  // 指定死信交换机
                .withArgument("x-dead-letter-routing-key", rabbitMQProperties.getDlqQueue())  // 指定死信路由键
                .withArgument("x-message-ttl", 5000)  // 消息在5秒后过期
                .build();
    }
    // 无死信队列的普通队列
    @Bean
    public Queue noDlqQueue() {
        return QueueBuilder.durable(rabbitMQProperties.getNoDlqQueue()).build();
    }

    // 死信队列
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(rabbitMQProperties.getDlqQueue()).build();
    }

    // 定义普通交换机
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(rabbitMQProperties.getExchangeName());
    }

    // 定义死信交换机
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(rabbitMQProperties.getDlxExchangeName());
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
}