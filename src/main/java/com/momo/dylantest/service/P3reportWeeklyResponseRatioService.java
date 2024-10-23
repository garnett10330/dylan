package com.momo.dylantest.service;


import com.momo.dylantest.model.P3ReportWeeklyResponseRatio;
import com.momo.dylantest.repository.P3ReportWeeklyResponseRatioRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
@Validated
@Service
@Slf4j
public class P3reportWeeklyResponseRatioService {
    @Resource
    private P3ReportWeeklyResponseRatioRepository p3ReportWeeklyResponseRatioRepository;

    public List<P3ReportWeeklyResponseRatio> findAll(){
        return p3ReportWeeklyResponseRatioRepository.findAll();
    }
    public List<P3ReportWeeklyResponseRatio> findByTicketId(String ticketId){
        return p3ReportWeeklyResponseRatioRepository.findByTicketId(ticketId);
    }
    public List<P3ReportWeeklyResponseRatio> findByTicketIdJPQL(String ticketId){
        return p3ReportWeeklyResponseRatioRepository.findByTicketIdJPQL(ticketId);
    }
    public List<P3ReportWeeklyResponseRatio> findByConditions(String entpCode, String goodsCode, Boolean eUnread){
        return p3ReportWeeklyResponseRatioRepository.findByConditions(entpCode, goodsCode, eUnread);
    }
}
