package com.momo.dylantest.mapper.mysql;

import com.momo.dylantest.model.mysql.P3ReportWeeklyResponseRatioPo;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface P3ReportWeeklyResponseRatioMapper {
    /**`sn` int(11) NOT NULL AUTO_INCREMENT COMMENT 'mysql流水號',
     `entpcode` varchar(10) DEFAULT NULL COMMENT '廠編',
     `goodscode` varchar(10) DEFAULT NULL COMMENT '品編',
     `c_msgtime` datetime DEFAULT NULL COMMENT 'user發文最早時間',
     `e_msgtime` datetime DEFAULT NULL COMMENT '廠商發文最早時間',
     `roomid` varchar(60) DEFAULT NULL COMMENT '聊天室id',
     `ticketid` varchar(100) NOT NULL COMMENT '聊天室ticket id',
     `e_unread` tinyint(4) DEFAULT NULL COMMENT '廠商未讀旗標_0已讀_1未讀',
     `resp_elapsed_hr` float DEFAULT NULL
     */

    @Select("SELECT * FROM `3Preport_weekly_response_ratio` WHERE ticketid = #{ticketId} ")
    @Results(id = "P3ReportWeeklyResponseRatio",
            value = {
                    @Result(property = "entpCode", column = "entpcode"),
                    @Result(property = "goodsCode", column = "goodscode"),
                    @Result(property = "cMsgTime", column = "c_msgtime"),
                    @Result(property = "eMsgTime", column = "e_msgtime"),
                    @Result(property = "roomId", column = "roomid"),
                    @Result(property = "ticketId", column = "ticketid"),
                    @Result(property = "eUnread", column = "e_unread"),
                    @Result(property = "respElapsedHr", column = "resp_elapsed_hr")
            }
    )
    List<P3ReportWeeklyResponseRatioPo> findByTicketId(@Param("ticketId") String ticketId);


    @Select({
            "<script>",
            "SELECT * FROM `3Preport_weekly_response_ratio`",
            "WHERE 1=1",
            "<if test='entpCode != null and entpCode != \"\"'>",
            "AND entpcode = #{entpCode}",
            "</if>",
            "<if test='goodsCode != null and goodsCode != \"\"'>",
            "AND goodscode = #{goodsCode}",
            "</if>",
            "<if test='eUnread != null'>",
            "AND e_unread = #{eUnread}",
            "</if>",
            "</script>"
    })
    @ResultMap("P3ReportWeeklyResponseRatio")
    List<P3ReportWeeklyResponseRatioPo> findByConditions(
            @Param("entpCode") String entpCode,
            @Param("goodsCode") String goodsCode,
            @Param("eUnread") Boolean eUnread
    );

    @Select( "SELECT * FROM `3Preport_weekly_response_ratio` ")
    @ResultMap("P3ReportWeeklyResponseRatio")
    List<P3ReportWeeklyResponseRatioPo> findAll3PReportWeeklyResponseRatio();
}
