package com.momo.dylantest.controller;

import com.momo.dylantest.model.dto.mysql.P3ReportWeeklyResponseRatioDto;
import com.momo.dylantest.model.request.ReportWeeklyConditionReq;
import com.momo.dylantest.response.Response;
import com.momo.dylantest.service.P3reportWeeklyResponseRatioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

@Validated
@Slf4j
@RestController
@RequestMapping("/v1/weeklyRatios")
public class TestController {

    @Resource
    private P3reportWeeklyResponseRatioService p3reportWeeklyResponseRatioService;


    /**
     * 查詢所有週回應比例數據。
     *
     * @return 包含所有週回應比例的列表。
     *         返回值包裝在 {@link Response} 中，數據類型為 {@link List}，
     *         每個元素為 {@link P3ReportWeeklyResponseRatioDto}。
     */
    @Operation(
            summary = "查詢所有週回應比例數據",
            description = "返回所有週回應比例數據的完整列表。",
            responses = {
                    @ApiResponse(responseCode = "200", description = "查詢成功",
                            content = @Content(schema = @Schema(implementation = P3ReportWeeklyResponseRatioDto.class))),
                    @ApiResponse(responseCode = "500", description = "伺服器錯誤")
            }
    )
    @GetMapping
    public Response<List<P3ReportWeeklyResponseRatioDto>> findAll(){
        return Response.success(p3reportWeeklyResponseRatioService.findAll());
    }

    /**
     * 根據 Ticket ID 查詢週回應比例數據。
     *
     * @param ticketId 工單 ID。
     * @return 匹配指定 Ticket ID 的週回應比例數據列表。
     *         返回值包裝在 {@link Response} 中，數據類型為 {@link List}，
     *         每個元素為 {@link P3ReportWeeklyResponseRatioDto}。
     */
    @Operation(
            summary = "根據 Ticket ID 查詢週回應比例數據",
            description = "根據提供的 Ticket ID 返回匹配的週回應比例數據。",
            parameters = @Parameter(name = "ticketId", description = "工單 ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "查詢成功",
                            content = @Content(schema = @Schema(implementation = P3ReportWeeklyResponseRatioDto.class))),
                    @ApiResponse(responseCode = "400", description = "參數錯誤"),
                    @ApiResponse(responseCode = "500", description = "伺服器錯誤")
            }
    )
    @GetMapping(params = "ticketId")
    public Response<List<P3ReportWeeklyResponseRatioDto>> findByTicketId(@RequestParam String ticketId){
        return Response.success(p3reportWeeklyResponseRatioService.findByTicketId(ticketId));
    }


    /**
     * 根據 Ticket ID 查詢週回應比例數據（有格式限制）。
     *
     * @param ticketId 工單 ID，必須是十位數的格式。
     * @return 匹配指定 Ticket ID 的週回應比例數據列表。
     *         返回值包裝在 {@link Response} 中，數據類型為 {@link List}，
     *         每個元素為 {@link P3ReportWeeklyResponseRatioDto}。
     */
    @Operation(
            summary = "根據 Ticket ID 查詢週回應比例數據（格式限制）",
            description = "根據提供的工單 ID（必須是十位數格式）返回匹配的週回應比例數據。",
            parameters = @Parameter(
                    name = "ticketId",
                    description = "工單 ID，必須是十位數格式",
                    required = true,
                    schema = @Schema(type = "string", pattern = "^[0-9]{10}$")
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "查詢成功",
                            content = @Content(schema = @Schema(implementation = P3ReportWeeklyResponseRatioDto.class))),
                    @ApiResponse(responseCode = "400", description = "參數格式錯誤"),
                    @ApiResponse(responseCode = "500", description = "伺服器錯誤")
            }
    )
    @GetMapping(path = "/vaildtest",params = "ticketId")
    public Response<List<P3ReportWeeklyResponseRatioDto>> findByTicketIdJPQL(
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
     * @param req 包含查詢條件的請求物件 {@link ReportWeeklyConditionReq}，其中包括：
     *            <ul>
     *                <li>{@code entpCode} - 企業代碼（必選）。</li>
     *                <li>{@code goodsCode} - 商品代碼（可選）。</li>
     *                <li>{@code eUnread} - 是否未讀標誌（可選）。</li>
     *            </ul>
     * @return 包裝在 {@link Response} 中的週回應比例數據列表。
     *         返回值類型為 {@link List}，其中每個元素為 {@link P3ReportWeeklyResponseRatioDto}。
     */
    @Operation(
            summary = "根據條件查詢週回應比例數據",
            description = "支持按企業代碼、商品代碼和未讀標誌進行查詢。企業代碼為必選，其餘為可選條件。",
            parameters = {
                    @Parameter(name = "entpCode", description = "企業代碼（必選）", required = true),
                    @Parameter(name = "goodsCode", description = "商品代碼（可選）", required = false),
                    @Parameter(name = "eUnread", description = "是否未讀標誌（可選）", required = false)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "查詢成功",
                            content = @Content(schema = @Schema(implementation = P3ReportWeeklyResponseRatioDto.class))),
                    @ApiResponse(responseCode = "400", description = "參數錯誤"),
                    @ApiResponse(responseCode = "500", description = "伺服器錯誤")
            }
    )
    @GetMapping("/conditions")
    public Response<List<P3ReportWeeklyResponseRatioDto>> findByConditions(@Valid ReportWeeklyConditionReq req) {
        return Response.success(p3reportWeeklyResponseRatioService.findByConditions(req));
    }




}
