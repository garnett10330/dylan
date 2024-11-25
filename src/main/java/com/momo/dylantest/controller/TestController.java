package com.momo.dylantest.controller;

import com.momo.dylantest.model.mysql.P3ReportWeeklyResponseRatioPo;
import com.momo.dylantest.response.Response;
import com.momo.dylantest.service.P3reportWeeklyResponseRatioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * @return 包含所有週回應比例的列表。{@link Response<java.util.List>}
     */
    @GetMapping
    public Response findAll(){
        return Response.success(p3reportWeeklyResponseRatioService.findAll());
    }

    /**
     * 根據Ticket ID查詢週回應比例數據。
     *
     * @param ticketId 工單 ID。
     * @return 匹配指定Ticket ID的週回應比例數據列表。{@link Response<java.util.List>}
     */
    @Operation(
            summary = "根據 Ticket ID 查詢週回應比例數據。",
            description = "匹配指定Ticket ID的週回應比例數據列表。",
            responses = {
                    @ApiResponse(responseCode = "200", description = "成功返回 匹配指定Ticket ID的週回應比例數據列表",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = P3ReportWeeklyResponseRatioPo.class)))),
                    @ApiResponse(responseCode = "500", description = "伺服器錯誤")
            }
    )
    @GetMapping(params = "ticketId")
    public Response findByTicketId(@Parameter(name = "ticketId", description = "工單 ID", required = true) @RequestParam String ticketId){
        return Response.success(p3reportWeeklyResponseRatioService.findByTicketId(ticketId));
    }
    /**
     * 使用 JPQL 根據 Ticket ID 查詢週回應比例數據。
     *
     * @param ticketId 工單 ID，必須是十位數的格式。
     * @return 匹配指定 Ticket ID 的週回應比例數據列表。{@link Response<java.util.List>}
     */
    @GetMapping(path = "/jpql",params = "ticketId")
    public Response findByTicketIdJPQL(
            @Pattern(regexp="^[0-9]{10}$", message="ticketId必須是十位數的格式")
            @RequestParam String ticketId){
        return Response.success(p3reportWeeklyResponseRatioService.findByTicketIdJPQL(ticketId));
    }
    /**
     * 根據條件查詢週回應比例數據。
     *
     * @param entpCode 企業代碼。
     * @param goodsCode 商品代碼。
     * @param eUnread 是否未讀標誌。
     * @return 匹配條件的週回應比例數據列表。{@link Response<java.util.List>}
     */
    @GetMapping("/conditions")
    public Response findByConditions(@RequestParam(required = false) String entpCode,@RequestParam(required = false) String goodsCode,@RequestParam(required = false) Boolean eUnread){
        return Response.success(p3reportWeeklyResponseRatioService.findByConditions(entpCode,goodsCode,eUnread));
    }




}
