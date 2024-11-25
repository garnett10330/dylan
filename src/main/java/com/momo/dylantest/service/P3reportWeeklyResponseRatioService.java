package com.momo.dylantest.service;


import com.momo.dylantest.mapper.mysql.MysqlDBMapper;
import com.momo.dylantest.model.dto.mysql.P3ReportWeeklyResponseRatioDto;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
/**
 * P3 報告每週回應比例服務類。
 *
 * 此服務類負責操作 P3 報告每週回應比例的數據。
 * 提供根據不同條件檢索數據的方法。
 */
@Validated
@Service
@Slf4j
public class P3reportWeeklyResponseRatioService {
    @Resource
    private MysqlDBMapper mysqlDBMapper;

    /**
     * 查詢所有的 P3 報告每週回應比例數據。
     *
     * @return 包含所有 P3 報告每週回應比例的列表。
     */
    public List<P3ReportWeeklyResponseRatioDto> findAll(){
        return mysqlDBMapper.findAll3PReportWeeklyResponseRatio().stream()
                .map(P3ReportWeeklyResponseRatioDto::new) // 使用建構子直接轉換
                .toList();
    }
    /**
     * 使用原生sql 根據工單 ID 查詢 P3 報告每週回應比例數據。
     *
     * @param ticketId 工單 ID。
     * @return 匹配指定工單 ID 的 P3 報告每週回應比例的列表。
     */
    public List<P3ReportWeeklyResponseRatioDto> findByTicketId(String ticketId){
        return mysqlDBMapper.findByTicketId(ticketId).stream()
                .map(P3ReportWeeklyResponseRatioDto::new) // 使用建構子直接轉換
                .toList();
    }

    /**
     * 根據條件自動生成查詢 P3 報告每週回應比例數據。
     *
     * @param entpCode 企業代碼。
     * @param goodsCode 商品代碼。
     * @param eUnread 是否為未讀標誌。
     * @return 匹配條件的 P3 報告每週回應比例的列表。
     */
    public List<P3ReportWeeklyResponseRatioDto> findByConditions(String entpCode, String goodsCode, Boolean eUnread){
        return mysqlDBMapper.findByConditions(entpCode, goodsCode, eUnread).stream()
                .map(P3ReportWeeklyResponseRatioDto::new) // 使用建構子直接轉換
                .toList();
    }
}
