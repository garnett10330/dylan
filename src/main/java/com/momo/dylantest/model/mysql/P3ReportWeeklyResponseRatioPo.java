package com.momo.dylantest.model.mysql;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "3Preport_weekly_response_ratio")
public class P3ReportWeeklyResponseRatioPo {
    /**`sn` int(11) NOT NULL AUTO_INCREMENT COMMENT 'mysql流水號',
     `entpcode` varchar(10) DEFAULT NULL COMMENT '廠編',
     `goodscode` varchar(10) DEFAULT NULL COMMENT '品編',
     `c_msgtime` datetime DEFAULT NULL COMMENT 'user發文最早時間',
     `e_msgtime` datetime DEFAULT NULL COMMENT '廠商發文最早時間',
     `roomid` varchar(60) DEFAULT NULL COMMENT '聊天室id',
     `ticketid` varchar(100) NOT NULL COMMENT '聊天室ticket id',
     `e_unread` tinyint(4) DEFAULT NULL COMMENT '廠商未讀旗標_0已讀_1未讀',
     `resp_elapsed_hr` float DEFAULT NULL*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sn;  // 使用 Integer 來匹配 int 類型

    @Column(name = "entpcode")
    private String entpCode;

    @Column(name = "goodscode")
    private String goodsCode;

    @Column(name = "c_msgtime")
    private LocalDateTime cMsgTime;

    @Column(name = "e_msgtime")
    private LocalDateTime eMsgTime;

    @Column(name = "roomid")
    private String roomId;

    @Column(name = "ticketid")
    private String ticketId;

    @Column(name = "e_unread")
    private Boolean eUnread;

    @Column(name = "resp_elapsed_hr")
    private BigDecimal respElapsedHr;

}
