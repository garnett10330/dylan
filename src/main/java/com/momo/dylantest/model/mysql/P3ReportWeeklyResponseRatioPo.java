package com.momo.dylantest.model.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class P3ReportWeeklyResponseRatioPo {
    /**
     * P3 Report Weekly Response Ratio Pojo
     *
     * 對應資料表欄位與註解如下：
     * `sn` int(11) NOT NULL AUTO_INCREMENT COMMENT 'mysql流水號',
     * `entpcode` varchar(10) DEFAULT NULL COMMENT '廠編',
     * `goodscode` varchar(10) DEFAULT NULL COMMENT '品編',
     * `c_msgtime` datetime DEFAULT NULL COMMENT 'user發文最早時間',
     * `e_msgtime` datetime DEFAULT NULL COMMENT '廠商發文最早時間',
     * `roomid` varchar(60) DEFAULT NULL COMMENT '聊天室id',
     * `ticketid` varchar(100) NOT NULL COMMENT '聊天室ticket id',
     * `e_unread` tinyint(4) DEFAULT NULL COMMENT '廠商未讀旗標_0已讀_1未讀',
     * `resp_elapsed_hr` float DEFAULT NULL COMMENT '回應耗時（小時）'
     */


    /**
     * mysql流水號
     */
    private long sn;

    /**
     * 廠編
     */
    private String entpCode;

    /**
     * 品編
     */
    private String goodsCode;

    /**
     * user發文最早時間
     */
    private LocalDateTime cMsgTime;

    /**
     * 廠商發文最早時間
     */
    private LocalDateTime eMsgTime;

    /**
     * 聊天室id
     */
    private String roomId;

    /**
     * 聊天室ticket id
     */
    private String ticketId;

    /**
     * 廠商未讀旗標_0已讀_1未讀
     */
    private Boolean eUnread;

    /**
     * 回應耗時（小時）
     */
    private BigDecimal respElapsedHr;

}
