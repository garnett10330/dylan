package com.momo.dylantest.service;

import com.momo.dylantest.mapper.mysql.MysqlDBMapper;
import com.momo.dylantest.model.dto.mysql.P3ReportWeeklyResponseRatioDto;
import com.momo.dylantest.model.request.ReportWeeklyConditionReq;
import com.momo.dylantest.service.test.P3reportWeeklyResponseRatioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class P3reportWeeklyResponseRatioServiceTest {

    @InjectMocks
    private P3reportWeeklyResponseRatioService service;

    @Mock
    private MysqlDBMapper mysqlDBMapper;

    @Test
    void testFindAll() {
        // Arrange
        List<P3ReportWeeklyResponseRatioDto> mockData = List.of(
                new P3ReportWeeklyResponseRatioDto(
                        "ENT123",                // entpCode
                        "GDS456",                // goodsCode
                        LocalDateTime.now(),     // cMsgTime
                        LocalDateTime.now(),     // eMsgTime
                        "ROOM123",               // roomId
                        "TICKET123",             // ticketId
                        true,                    // eUnread
                        BigDecimal.valueOf(12.34) // respElapsedHr
                ),
                new P3ReportWeeklyResponseRatioDto(
                        "ENT789",                // entpCode
                        "GDS987",                // goodsCode
                        LocalDateTime.now().minusDays(1), // cMsgTime
                        LocalDateTime.now().minusDays(1), // eMsgTime
                        "ROOM456",               // roomId
                        "TICKET456",             // ticketId
                        false,                   // eUnread
                        BigDecimal.valueOf(7.89) // respElapsedHr
                )
        );
        when(mysqlDBMapper.findAll3PReportWeeklyResponseRatio()).thenReturn(mockData);

        // Act
        List<P3ReportWeeklyResponseRatioDto> result = service.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(mysqlDBMapper, times(1)).findAll3PReportWeeklyResponseRatio();
    }

    @Test
    void testFindByTicketId() {
        // Arrange
        String ticketId = "T12345";
        List<P3ReportWeeklyResponseRatioDto> mockData = List.of(
                new P3ReportWeeklyResponseRatioDto(
                        "ENT123",                // entpCode
                        "GDS456",                // goodsCode
                        LocalDateTime.now(),     // cMsgTime
                        LocalDateTime.now(),     // eMsgTime
                        "ROOM123",               // roomId
                        "T12345",             // ticketId
                        true,                    // eUnread
                        BigDecimal.valueOf(12.34) // respElapsedHr
                )
        );
        when(mysqlDBMapper.findByTicketId(ticketId)).thenReturn(mockData);

        // Act
        List<P3ReportWeeklyResponseRatioDto> result = service.findByTicketId(ticketId);

        // Assert
        assertEquals(1, result.size());
        verify(mysqlDBMapper, times(1)).findByTicketId(ticketId);
    }

    @Test
    void testFindByConditions() {
        // Arrange
        ReportWeeklyConditionReq req = new ReportWeeklyConditionReq();
        req.setEntpCode("ENT123");
        req.setGoodsCode("GDS456");
        req.setEUnread(true);

        List<P3ReportWeeklyResponseRatioDto> mockData = List.of(
                new P3ReportWeeklyResponseRatioDto(
                        "ENT123",                // entpCode
                        "GDS456",                // goodsCode
                        LocalDateTime.now(),     // cMsgTime
                        LocalDateTime.now(),     // eMsgTime
                        "ROOM123",               // roomId
                        "TICKET123",             // ticketId
                        true,                    // eUnread
                        BigDecimal.valueOf(12.34) // respElapsedHr
                )
        );
        when(mysqlDBMapper.findByConditions(req.getEntpCode(), req.getGoodsCode(), req.getEUnread()))
                .thenReturn(mockData);

        // Act
        List<P3ReportWeeklyResponseRatioDto> result = service.findByConditions(req);

        // Assert
        assertEquals(1, result.size());
        verify(mysqlDBMapper, times(1))
                .findByConditions(req.getEntpCode(), req.getGoodsCode(), req.getEUnread());
    }
}
