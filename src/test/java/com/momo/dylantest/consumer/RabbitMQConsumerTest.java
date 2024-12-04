package com.momo.dylantest.consumer;

import com.momo.dylantest.properties.RabbitMQConfigProperties;
import com.rabbitmq.client.Channel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Message;

import java.io.IOException;

import static org.mockito.Mockito.*;

class RabbitMQConsumerTest {

    @InjectMocks
    private RabbitMQConsumer rabbitMQConsumer;

    @Mock
    private RabbitMQConfigProperties rabbitMQConfigProperties;

    @Mock
    private Channel channel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReceiveNormalQueueSuccess() throws Exception {
        Message mockMessage = new Message("test message".getBytes());

        rabbitMQConsumer.receiveNormalQueue(mockMessage, 12345L, channel);

        verify(channel, times(1)).basicAck(12345L, false);
    }

    @Test
    void testReceiveNormalQueueFailure() throws Exception {
        Message mockMessage = new Message("fail message".getBytes());

        rabbitMQConsumer.receiveNormalQueue(mockMessage, 12345L, channel);

        verify(channel, times(1)).basicNack(12345L, false, false);
    }

    @Test
    void testReceiveNoDlqQueueSuccess() throws Exception {
        Message mockMessage = new Message("test message".getBytes());

        rabbitMQConsumer.receiveNoDlqQueue(mockMessage, 12345L, channel);

        verify(channel, times(1)).basicAck(12345L, false);
    }

    @Test
    void testReceiveNoDlqQueueFailure() throws Exception {
        Message mockMessage = new Message("test message".getBytes());
        doThrow(new IOException("Test Exception")).when(channel).basicAck(anyLong(), anyBoolean());

        rabbitMQConsumer.receiveNoDlqQueue(mockMessage, 12345L, channel);

        verify(channel, times(1)).basicNack(12345L, false, false);
    }

    @Test
    void testReceiveDeadLetterQueueSuccess() throws Exception {
        Message mockMessage = new Message("dead letter message".getBytes());

        rabbitMQConsumer.receiveDeadLetterQueue(mockMessage, channel, 12345L);

        verify(channel, times(1)).basicAck(12345L, false);
    }

    @Test
    void testReceiveDeadLetterQueueFailure() throws Exception {
        Message mockMessage = new Message("dead letter message".getBytes());
        doThrow(new IOException("Test Exception")).when(channel).basicAck(anyLong(), anyBoolean());

        rabbitMQConsumer.receiveDeadLetterQueue(mockMessage, channel, 12345L);

        verify(channel, times(1)).basicNack(12345L, false, false);
    }
}
