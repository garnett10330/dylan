package com.momo.dylantest.controller;

import com.momo.dylantest.controller.message.MessageProducerController;
import com.momo.dylantest.service.message.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class MessageProducerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageProducerController messageProducerController;

    public MessageProducerControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(messageProducerController).build();
    }

    @Test
    void testSendToNormal() throws Exception {
        String testMessage = "TestMessage";
        when(messageService.sendToNormal(anyString())).thenReturn("Message Sent to Normal Queue");

        String requestBody = """
                {
                    "message": "TestMessage"
                }
                """;

        mockMvc.perform(post("/api/message/queue/normal/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").value("Message Sent to Normal Queue"));

        verify(messageService, times(1)).sendToNormal(anyString());
    }

    @Test
    void testSendToNoDlq() throws Exception {
        String testMessage = "TestMessage";
        when(messageService.sendToNoDlq(anyString())).thenReturn("Message Sent to No DLQ");

        String requestBody = """
                {
                    "message": "TestMessage"
                }
                """;

        mockMvc.perform(post("/api/message/queue/no-dlq/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").value("Message Sent to No DLQ"));

        verify(messageService, times(1)).sendToNoDlq(anyString());
    }

    @Test
    void testSendMutiToNoDlq() throws Exception {
        String testMessage = "BatchMessage";
        when(messageService.sendMutiToNoDlq(anyString())).thenReturn("Batch Messages Sent to No DLQ");

        String requestBody = """
                {
                    "message": "BatchMessage"
                }
                """;

        mockMvc.perform(post("/api/message/queue/no-dlq/batch/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").value("Batch Messages Sent to No DLQ"));

        verify(messageService, times(1)).sendMutiToNoDlq(anyString());
    }
}
