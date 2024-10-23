package com.momo.dylantest.repository;

import com.momo.dylantest.model.P3ReportWeeklyResponseRatio;
import com.momo.dylantest.repository.custom.customP3ReportWeeklyResponseRatio.CustomP3WeeklyResponseRatioRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface P3ReportWeeklyResponseRatioRepository extends JpaRepository<P3ReportWeeklyResponseRatio, Integer>, CustomP3WeeklyResponseRatioRepository {
    @Query(value = "SELECT * FROM `3Preport_weekly_response_ratio` WHERE ticketid = :ticketId", nativeQuery = true)
    List<P3ReportWeeklyResponseRatio> findByTicketId(@Param("ticketId") String ticketId);
    @Query("SELECT w FROM P3ReportWeeklyResponseRatio w WHERE w.ticketId = :ticketId")
    List<P3ReportWeeklyResponseRatio> findByTicketIdJPQL(@Param("ticketId") String ticketId);
}
