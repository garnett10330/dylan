package com.momo.dylantest.model.dto.mysql;

import com.momo.dylantest.model.mysql.P3ReportWeeklyResponseRatioPo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor // 自動生成預設建構子
public class P3ReportWeeklyResponseRatioDto {


    @Schema(description = "廠編")
    private String entpCode;

    @Schema(description = "品編")
    private String goodsCode;

    @Schema(description = "user發文最早時間")
    private LocalDateTime cMsgTime;

    @Schema(description = "廠商發文最早時間")
    private LocalDateTime eMsgTime;

    @Schema(description = "聊天室id")
    private String roomId;

    @Schema(description = "聊天室ticket id")
    private String ticketId;

    @Schema(description = "廠商未讀旗標_0已讀_1未讀")
    private Boolean eUnread;

    @Schema(description = "test")
    private BigDecimal respElapsedHr;

    // 從 P3ReportWeeklyResponseRatioPo 初始化 Dto 的建構子
    public P3ReportWeeklyResponseRatioDto(P3ReportWeeklyResponseRatioPo po) {
        this.entpCode = po.getEntpCode();
        this.goodsCode = po.getGoodsCode();
        this.cMsgTime = po.getCMsgTime();
        this.eMsgTime = po.getEMsgTime();
        this.roomId = po.getRoomId();
        this.ticketId = po.getTicketId();
        this.eUnread = po.getEUnread() == null ? null : po.getEUnread() == true; // 將 TinyInt 轉為 Boolean
        this.respElapsedHr = po.getRespElapsedHr();
    }
}
