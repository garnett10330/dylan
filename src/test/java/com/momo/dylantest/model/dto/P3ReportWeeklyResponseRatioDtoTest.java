package com.momo.dylantest.model.dto;

import com.momo.dylantest.model.dto.mysql.P3ReportWeeklyResponseRatioDto;
import com.momo.dylantest.model.mysql.P3ReportWeeklyResponseRatioPo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class P3ReportWeeklyResponseRatioDtoTest {

    @Test
    void testNoArgsConstructor() {
        // 測試無參建構子
        P3ReportWeeklyResponseRatioDto dto = new P3ReportWeeklyResponseRatioDto();
        assertNotNull(dto, "DTO should be created with no-args constructor");
    }

    @Test
    void testAllArgsConstructor() {
        // 測試全參建構子
        LocalDateTime now = LocalDateTime.now();
        P3ReportWeeklyResponseRatioDto dto = new P3ReportWeeklyResponseRatioDto(
                "ENTP001",
                "GOOD001",
                now,
                now.plusHours(1),
                "ROOM001",
                "TICKET001",
                true,
                BigDecimal.valueOf(2.5)
        );

        assertEquals("ENTP001", dto.getEntpCode());
        assertEquals("GOOD001", dto.getGoodsCode());
        assertEquals(now, dto.getCMsgTime());
        assertEquals(now.plusHours(1), dto.getEMsgTime());
        assertEquals("ROOM001", dto.getRoomId());
        assertEquals("TICKET001", dto.getTicketId());
        assertTrue(dto.getEUnread());
        assertEquals(BigDecimal.valueOf(2.5), dto.getRespElapsedHr());
    }

    @Test
    void testPoBasedConstructorWithValidPo() {
        // 測試基於 Po 的建構子
        LocalDateTime now = LocalDateTime.now();
        P3ReportWeeklyResponseRatioPo po = new P3ReportWeeklyResponseRatioPo();
        po.setEntpCode("ENTP001");
        po.setGoodsCode("GOOD001");
        po.setCMsgTime(now);
        po.setEMsgTime(now.plusHours(1));
        po.setRoomId("ROOM001");
        po.setTicketId("TICKET001");
        po.setEUnread(true);
        po.setRespElapsedHr(BigDecimal.valueOf(2.5));

        P3ReportWeeklyResponseRatioDto dto = new P3ReportWeeklyResponseRatioDto(po);

        assertEquals("ENTP001", dto.getEntpCode());
        assertEquals("GOOD001", dto.getGoodsCode());
        assertEquals(now, dto.getCMsgTime());
        assertEquals(now.plusHours(1), dto.getEMsgTime());
        assertEquals("ROOM001", dto.getRoomId());
        assertEquals("TICKET001", dto.getTicketId());
        assertTrue(dto.getEUnread());
        assertEquals(BigDecimal.valueOf(2.5), dto.getRespElapsedHr());
    }

    @Test
    void testPoBasedConstructorWithNullFields() {
        // 測試 Po 中所有字段為 null 的情況
        P3ReportWeeklyResponseRatioPo po = new P3ReportWeeklyResponseRatioPo();

        P3ReportWeeklyResponseRatioDto dto = new P3ReportWeeklyResponseRatioDto(po);

        assertNull(dto.getEntpCode());
        assertNull(dto.getGoodsCode());
        assertNull(dto.getCMsgTime());
        assertNull(dto.getEMsgTime());
        assertNull(dto.getRoomId());
        assertNull(dto.getTicketId());
        assertNull(dto.getEUnread());
        assertNull(dto.getRespElapsedHr());
    }

    @Test
    void testSettersAndGetters() {
        // 測試所有 getter 和 setter
        LocalDateTime now = LocalDateTime.now();
        P3ReportWeeklyResponseRatioDto dto = new P3ReportWeeklyResponseRatioDto();

        dto.setEntpCode("ENTP001");
        dto.setGoodsCode("GOOD001");
        dto.setCMsgTime(now);
        dto.setEMsgTime(now.plusHours(1));
        dto.setRoomId("ROOM001");
        dto.setTicketId("TICKET001");
        dto.setEUnread(false);
        dto.setRespElapsedHr(BigDecimal.valueOf(3.0));

        assertEquals("ENTP001", dto.getEntpCode());
        assertEquals("GOOD001", dto.getGoodsCode());
        assertEquals(now, dto.getCMsgTime());
        assertEquals(now.plusHours(1), dto.getEMsgTime());
        assertEquals("ROOM001", dto.getRoomId());
        assertEquals("TICKET001", dto.getTicketId());
        assertFalse(dto.getEUnread());
        assertEquals(BigDecimal.valueOf(3.0), dto.getRespElapsedHr());
    }
}