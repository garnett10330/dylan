package com.momo.dylantest.controller;

import com.momo.dylantest.controller.test.TestController;
import com.momo.dylantest.model.dto.mysql.P3ReportWeeklyResponseRatioDto;
import com.momo.dylantest.model.request.ReportWeeklyConditionReq;
import com.momo.dylantest.service.test.P3reportWeeklyResponseRatioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class TestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private P3reportWeeklyResponseRatioService p3reportWeeklyResponseRatioService;

    @InjectMocks
    private TestController testController;

    public TestControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(testController).build();
    }

    @Test
    void testFindAll() throws Exception {
        List<P3ReportWeeklyResponseRatioDto> mockResponse = List.of(new P3ReportWeeklyResponseRatioDto());
        when(p3reportWeeklyResponseRatioService.findAll()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/weeklyRatios/v1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray());

        verify(p3reportWeeklyResponseRatioService, times(1)).findAll();
    }

    @Test
    void testFindByTicketId() throws Exception {
        String ticketId = "12345";
        List<P3ReportWeeklyResponseRatioDto> mockResponse = List.of(new P3ReportWeeklyResponseRatioDto());
        when(p3reportWeeklyResponseRatioService.findByTicketId(ticketId)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/weeklyRatios/v1")
                        .param("ticketId", ticketId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray());

        verify(p3reportWeeklyResponseRatioService, times(1)).findByTicketId(ticketId);
    }

    @Test
    void testFindByTicketIdValid() throws Exception {
        String validTicketId = "1234567890";
        List<P3ReportWeeklyResponseRatioDto> mockResponse = List.of(new P3ReportWeeklyResponseRatioDto());
        when(p3reportWeeklyResponseRatioService.findByTicketId(validTicketId)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/weeklyRatios/vaildtest/v1")
                        .param("ticketId", validTicketId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray());

        verify(p3reportWeeklyResponseRatioService, times(1)).findByTicketId(validTicketId);
    }

    @Test
    void testFindByConditions() throws Exception {
        ReportWeeklyConditionReq req = new ReportWeeklyConditionReq();
        req.setEntpCode("E123");
        req.setGoodsCode("G123");
        req.setEUnread(true);

        List<P3ReportWeeklyResponseRatioDto> mockResponse = List.of(new P3ReportWeeklyResponseRatioDto());
        when(p3reportWeeklyResponseRatioService.findByConditions(any(ReportWeeklyConditionReq.class))).thenReturn(mockResponse);

        mockMvc.perform(get("/api/weeklyRatios/conditions/v1")
                        .param("entpCode", req.getEntpCode())
                        .param("goodsCode", req.getGoodsCode())
                        .param("eUnread", String.valueOf(req.getEUnread())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray());

        verify(p3reportWeeklyResponseRatioService, times(1)).findByConditions(any(ReportWeeklyConditionReq.class));
    }
}

