package com.momo.dylantest.controller;//package com.momo.dylantest.controller;

import com.momo.dylantest.model.mysql.CompanyStockMysqlPo;
import com.momo.dylantest.response.Response;
import com.momo.dylantest.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
/**
 * 消息生產者控制器。
 * 測試使用，用於將消息發送到普通對列或無死信對列。
 */
@RestController
@RequestMapping("/v1/message")
public class MessageProducerController {

    @Resource
    private MessageService messageService;

    /**
     * 發送消息到普通佇列。
     *
     * @param message 要發送的消息內容。
     * @return 成功發送消息的回應。
     */
    @Operation(
            summary = "發送消息到普通佇列",
            description = "將指定的消息內容發送到普通佇列。",
            parameters = @Parameter(name = "message", description = "要發送的消息內容", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "消息成功發送",
                            content = @Content(schema = @Schema(type = "string"))),
                    @ApiResponse(responseCode = "400", description = "請求參數錯誤"),
                    @ApiResponse(responseCode = "500", description = "伺服器錯誤")
            }
    )
    @PostMapping("/queue/normal")
    public Response<String> sendToNormal(@RequestParam String message) {
        CompanyStockMysqlPo companyStock = new CompanyStockMysqlPo();
        companyStock.setId(12345);
        companyStock.setGmtCreated(LocalDateTime.now());
        companyStock.setSymbol(message);
        return Response.success(messageService.sendToNormal(companyStock.toString()));
    }

    /**
     * 發送消息到無死信佇列。
     *
     * @param message 要發送的消息內容。
     * @return 成功發送消息的回應。
     */
    @Operation(
            summary = "發送消息到無死信佇列",
            description = "將指定的消息內容發送到無死信佇列。",
            parameters = @Parameter(name = "message", description = "要發送的消息內容", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "消息成功發送",
                            content = @Content(schema = @Schema(type = "string"))),
                    @ApiResponse(responseCode = "400", description = "請求參數錯誤"),
                    @ApiResponse(responseCode = "500", description = "伺服器錯誤")
            }
    )
    @PostMapping("/queue/no-dlq")
    public Response<String> sendToNoDlq(@RequestParam String message) {
        return Response.success(messageService.sendToNoDlq(message));
    }

    /**
     * 批量發送消息到無死信佇列。
     *
     * @param message 要發送的消息內容，每條消息將附加唯一的識別符。
     * @return 成功發送批量消息的回應。
     */
    @Operation(
            summary = "批量發送消息到無死信佇列",
            description = "將指定的消息內容以批量方式發送到無死信佇列，每條消息將附加唯一識別符。",
            parameters = @Parameter(name = "message", description = "要批量發送的消息內容", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "批量消息成功發送",
                            content = @Content(schema = @Schema(type = "string"))),
                    @ApiResponse(responseCode = "400", description = "請求參數錯誤"),
                    @ApiResponse(responseCode = "500", description = "伺服器錯誤")
            }
    )
    @PostMapping("/queue/no-dlq/batch")
    public Response<String> sendMutiToNoDlq(@RequestParam String message) {
        return Response.success(messageService.sendMutiToNoDlq(message));
    }
}
