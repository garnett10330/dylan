package com.momo.dylantest.controller.test;

import com.momo.dylantest.model.dto.mysql.P3ReportWeeklyResponseRatioDto;
import com.momo.dylantest.model.request.ReportWeeklyConditionReq;
import com.momo.dylantest.response.Response;
import com.momo.dylantest.service.test.P3reportWeeklyResponseRatioService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * 測試控制器。
 * 主要測試db沒規範的數據展示。
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/api/weeklyRatios")
public class TestController {

    @Resource
    private P3reportWeeklyResponseRatioService p3reportWeeklyResponseRatioService;


    /**
     * 查詢所有週回應比例數據。
     * <p>
     * 返回所有週回應比例數據的完整列表。
     * </p>
     * @return 包含所有週回應比例的列表。
     */
    @GetMapping("/v1")
    public Response<List<P3ReportWeeklyResponseRatioDto>> findAll(){
        return Response.success(p3reportWeeklyResponseRatioService.findAll());
    }

    /**
     * 根據 Ticket ID 查詢週回應比例數據。
     * <p>
     * 根據提供的 Ticket ID 返回匹配的週回應比例數據。
     * </p>
     * @param ticketId 工單 ID。
     * @return 匹配指定 Ticket ID 的週回應比例數據列表。
     *         返回值包裝在 {@link Response} 中，數據類型為 {@link List}，
     *         每個元素為 {@link P3ReportWeeklyResponseRatioDto}。
     */
    @GetMapping(path="/v1",params = "ticketId")
    public Response<List<P3ReportWeeklyResponseRatioDto>> findByTicketId(@RequestParam String ticketId){
        return Response.success(p3reportWeeklyResponseRatioService.findByTicketId(ticketId));
    }


    /**
     * 根據 Ticket ID 查詢週回應比例數據（有格式限制）。
     *
     * @param ticketId 工單 ID，必須是十位數的格式。
     * @return 匹配指定 Ticket ID 的週回應比例數據列表。
     */

    @GetMapping(path = "/vaildtest/v1",params = "ticketId")
    public Response<List<P3ReportWeeklyResponseRatioDto>> findByTicketIdVaild(
            @Pattern(regexp="^[0-9]{10}$", message="ticketId必須是十位數的格式")
            @RequestParam String ticketId){
        return Response.success(p3reportWeeklyResponseRatioService.findByTicketId(ticketId));
    }
    /**
     * 根據條件查詢週回應比例數據。
     * <p>
     * 支持按企業代碼、商品代碼和未讀標誌進行查詢。企業代碼為必填，其餘為可選。
     * 如果未提供其他條件，將返回匹配企業代碼的所有數據。
     * </p>
     *
     * @param req 包含查詢條件的請求物件，其中包括：
     *            <ul>
     *                <li>{@code entpCode} - 企業代碼（必選）。</li>
     *                <li>{@code goodsCode} - 商品代碼（可選）。</li>
     *                <li>{@code eUnread} - 是否未讀標誌（可選）。</li>
     *            </ul>
     * @return 根據條件查詢週回應比例數據

     */
    @GetMapping("/conditions/v1")
    public Response<List<P3ReportWeeklyResponseRatioDto>> findByConditions(@Valid ReportWeeklyConditionReq req) {
        return Response.success(p3reportWeeklyResponseRatioService.findByConditions(req));
    }




}
