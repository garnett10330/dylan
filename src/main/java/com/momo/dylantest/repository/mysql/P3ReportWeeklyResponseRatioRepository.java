package com.momo.dylantest.repository.mysql;

import com.momo.dylantest.model.mysql.P3ReportWeeklyResponseRatioPo;
import com.momo.dylantest.repository.mysql.custom.customP3ReportWeeklyResponseRatio.CustomP3WeeklyResponseRatioRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface P3ReportWeeklyResponseRatioRepository extends JpaRepository<P3ReportWeeklyResponseRatioPo, Integer>, CustomP3WeeklyResponseRatioRepository {
    @Query(value = "SELECT * FROM `3Preport_weekly_response_ratio` WHERE ticketid = :ticketId", nativeQuery = true)
    List<P3ReportWeeklyResponseRatioPo> findByTicketId(@Param("ticketId") String ticketId);
    @Query("SELECT w FROM P3ReportWeeklyResponseRatioPo w WHERE w.ticketId = :ticketId")
    List<P3ReportWeeklyResponseRatioPo> findByTicketIdJPQL(@Param("ticketId") String ticketId);
}
