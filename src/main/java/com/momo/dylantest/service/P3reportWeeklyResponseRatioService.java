package com.momo.dylantest.service;


import com.momo.dylantest.mapper.mysql.MysqlDBMapper;
import com.momo.dylantest.model.dto.mysql.P3ReportWeeklyResponseRatioDto;
import com.momo.dylantest.model.request.ReportWeeklyConditionReq;
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
     * 查詢所有的 3p 報告每週回應比例數據。
     * <p>
     * 該方法從資料庫中檢索所有 3p 報告的每週回應比例數據，
     * 並將數據轉換為 DTO 對象以返回給調用方。
     * </p>
     *
     * @return 包含所有 3p 報告每週回應比例的列表。
     *         返回值類型為 {@link List}<{@link P3ReportWeeklyResponseRatioDto}>。
     */
    public List<P3ReportWeeklyResponseRatioDto> findAll(){
        return mysqlDBMapper.findAll3PReportWeeklyResponseRatio();
    }
    /**
     * 使用原生 SQL 根據工單 ID 查詢 P3 報告每週回應比例數據。
     * <p>
     * 該方法使用提供的工單 ID 從資料庫中檢索匹配的數據，
     * 並將結果轉換為 DTO 對象以返回給調用方。
     * </p>
     *
     * @param ticketId 工單 ID，用於查詢匹配的 P3 報告數據。
     * @return 匹配指定工單 ID 的 P3 報告每週回應比例數據列表。
     *         返回值類型為 {@link List}<{@link P3ReportWeeklyResponseRatioDto}>。
     */
    public List<P3ReportWeeklyResponseRatioDto> findByTicketId(String ticketId){
        return mysqlDBMapper.findByTicketId(ticketId);
    }

    /**
     * 根據條件查詢 P3 報告每週回應比例數據。
     * <p>
     * 該方法根據提供的條件動態生成 SQL 查詢，從資料庫中檢索匹配的數據，
     * 並將結果轉換為 DTO 對象以返回給調用方。
     * </p>
     *
     * @param req 包含查詢條件的請求對象 {@link ReportWeeklyConditionReq}，其中包括：
     *            <ul>
     *                <li>{@code entpCode} - 企業代碼（必填）。</li>
     *                <li>{@code goodsCode} - 商品代碼（可選）。</li>
     *                <li>{@code eUnread} - 是否為未讀標誌（可選）。</li>
     *            </ul>
     * @return 匹配條件的 P3 報告每週回應比例數據列表。
     *         返回值類型為 {@link List}<{@link P3ReportWeeklyResponseRatioDto}>。
     */
    public List<P3ReportWeeklyResponseRatioDto> findByConditions(ReportWeeklyConditionReq req){
        return mysqlDBMapper.findByConditions(req.getEntpCode(), req.getGoodsCode(), req.getEUnread());
    }
}
