package com.momo.dylantest.config;

import com.momo.dylantest.properties.RabbitMQConfigProperties;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RabbitMQConfigTest {

    @InjectMocks
    private RabbitMQConfig rabbitMQConfig;

    @Mock
    private RabbitMQConfigProperties rabbitMQConfigProperties;

    @Mock
    private ConnectionFactory connectionFactory;

    @Test
    void testNormalQueue() {
        when(rabbitMQConfigProperties.getNormalQueue()).thenReturn("normalQueue");
        when(rabbitMQConfigProperties.getDlxExchangeName()).thenReturn("dlxExchange");
        when(rabbitMQConfigProperties.getDlqQueue()).thenReturn("deadLetterQueue");

        Queue queue = rabbitMQConfig.normalQueue();
        assertNotNull(queue);
        assertEquals("normalQueue", queue.getName());
        assertEquals("dlxExchange", queue.getArguments().get("x-dead-letter-exchange"));
        assertEquals("deadLetterQueue", queue.getArguments().get("x-dead-letter-routing-key"));
    }

    @Test
    void testNoDlqQueue() {
        when(rabbitMQConfigProperties.getNoDlqQueue()).thenReturn("noDlqQueue");

        Queue queue = rabbitMQConfig.noDlqQueue();
        assertNotNull(queue);
        assertEquals("noDlqQueue", queue.getName());
    }

    @Test
    void testDeadLetterQueue() {
        when(rabbitMQConfigProperties.getDlqQueue()).thenReturn("deadLetterQueue");

        Queue queue = rabbitMQConfig.deadLetterQueue();
        assertNotNull(queue);
        assertEquals("deadLetterQueue", queue.getName());
    }

    @Test
    void testExchange() {
        when(rabbitMQConfigProperties.getExchangeName()).thenReturn("normalExchange");

        DirectExchange exchange = rabbitMQConfig.exchange();
        assertNotNull(exchange);
        assertEquals("normalExchange", exchange.getName());
    }

    @Test
    void testDlxExchange() {
        when(rabbitMQConfigProperties.getDlxExchangeName()).thenReturn("dlxExchange");

        DirectExchange dlxExchange = rabbitMQConfig.dlxExchange();
        assertNotNull(dlxExchange);
        assertEquals("dlxExchange", dlxExchange.getName());
    }

    @Test
    void testNormalBinding() {
        Queue mockQueue = new Queue("normalQueue");
        DirectExchange mockExchange = new DirectExchange("normalExchange");

        Binding binding = rabbitMQConfig.normalBinding(mockQueue, mockExchange);
        assertNotNull(binding);
        assertEquals("normalQueue", binding.getRoutingKey());
        assertEquals("normalExchange", binding.getExchange());
    }

    @Test
    void testNoDlqBinding() {
        Queue mockQueue = new Queue("noDlqQueue");
        DirectExchange mockExchange = new DirectExchange("normalExchange");

        Binding binding = rabbitMQConfig.noDlqBinding(mockQueue, mockExchange);
        assertNotNull(binding);
        assertEquals("noDlqQueue", binding.getRoutingKey());
        assertEquals("normalExchange", binding.getExchange());
    }

    @Test
    void testDlqBinding() {
        Queue mockQueue = new Queue("deadLetterQueue");
        DirectExchange mockExchange = new DirectExchange("dlxExchange");

        Binding binding = rabbitMQConfig.dlqBinding(mockQueue, mockExchange);
        assertNotNull(binding);
        assertEquals("deadLetterQueue", binding.getRoutingKey());
        assertEquals("dlxExchange", binding.getExchange());
    }

    @Test
    void testRabbitTemplate() {
        RabbitTemplate rabbitTemplate = rabbitMQConfig.rabbitTemplate(connectionFactory);

        // 模擬 ConfirmCallback 並驗證行為
        RabbitTemplate.ConfirmCallback mockCallback = mock(RabbitTemplate.ConfirmCallback.class);

        // 測試成功的確認行為
        mockCallback.confirm(null, true, null);
        verify(mockCallback, times(1)).confirm(null, true, null);

        // 測試失敗的確認行為
        mockCallback.confirm(null, false, "Test Failure");
        verify(mockCallback, times(1)).confirm(null, false, "Test Failure");
    }
}
