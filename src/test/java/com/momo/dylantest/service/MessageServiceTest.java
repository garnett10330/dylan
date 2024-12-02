package com.momo.dylantest.service;

import com.momo.dylantest.producer.RabbitMQProducer;
import com.momo.dylantest.service.message.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @InjectMocks
    private MessageService messageService;

    @Mock
    private RabbitMQProducer rabbitMQProducer;

    @Test
    void testSendToNormal() {
        // Arrange
        String message = "TestMessage";

        // Act
        String result = messageService.sendToNormal(message);

        // Assert
        verify(rabbitMQProducer, times(1)).sendToNormalQueue(message);
        assertEquals("Message sent to normal queue!", result);
    }

    @Test
    void testSendToNoDlq() {
        // Arrange
        String message = "TestMessage";

        // Act
        String result = messageService.sendToNoDlq(message);

        // Assert
        verify(rabbitMQProducer, times(1)).sendToNoDlqQueue(message);
        assertEquals("Message sent to no-dlq queue!", result);
    }

    @Test
    void testSendMutiToNoDlq() {
        // Arrange
        String message = "BatchMessage";
        doNothing().when(rabbitMQProducer).sendToNoDlqQueue(anyString());

        // Act
        String result = messageService.sendMutiToNoDlq(message);

        // Assert
        verify(rabbitMQProducer, times(5000)).sendToNoDlqQueue(anyString());
        assertEquals("5000 messages sent concurrently to no-dlq queue!", result);
    }
}
