package com.momo.dylantest.controller.message;//package com.momo.dylantest.controller;

import com.momo.dylantest.model.mysql.CompanyStockMysqlPo;
import com.momo.dylantest.model.request.MessageTestReq;
import com.momo.dylantest.response.Response;
import com.momo.dylantest.service.message.MessageService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
/**
 * 消息生產者控制器。
 * 測試使用，用於將消息發送到普通對列或無死信對列。
 */
@Validated
@RestController
@RequestMapping("/api/message")
public class MessageProducerController {

    @Resource
    private MessageService messageService;

    /**
     * 發送消息到普通佇列。
     * <p>
     * 將指定的消息內容發送到普通佇列。
     * </p>
     * @param messageTestReq 包含發送message的請求物件 {@link MessageTestReq}。
     * @return 成功發送消息的回應。
     */
    @PostMapping("/queue/normal/v1")
    public Response<String> sendToNormal(@Valid @RequestBody MessageTestReq messageTestReq) {
        CompanyStockMysqlPo companyStock = new CompanyStockMysqlPo();
        companyStock.setId(12345);
        companyStock.setGmtCreated(LocalDateTime.now());
        companyStock.setSymbol(messageTestReq.getMessage());
        return Response.success(messageService.sendToNormal(companyStock.toString()));
    }

    /**
     * 發送消息到無死信佇列。
     * <p>
     * 將指定的消息內容發送到無死信佇列。
     * </p>
     *
     * @param messageTestReq 包含發送message的請求物件 {@link MessageTestReq}。
     * @return 成功發送消息的回應。
     */
    @PostMapping("/queue/no-dlq/v1")
    public Response<String> sendToNoDlq(@Valid @RequestBody MessageTestReq messageTestReq) {
        return Response.success(messageService.sendToNoDlq(messageTestReq.getMessage()));
    }

    /**
     * 批量發送消息到無死信佇列。
     * <p>
     * 將指定的消息內容以批量方式發送到無死信佇列，每條消息將附加唯一識別符。
     * </p>
     * @param messageTestReq 包含發送message的請求物件 每條消息將附加唯一的識別符 {@link MessageTestReq}。
     * @return 成功發送批量消息的回應。
     */
    @PostMapping("/queue/no-dlq/batch/v1")
    public Response<String> sendMutiToNoDlq(@Valid @RequestBody MessageTestReq messageTestReq) {
        return Response.success(messageService.sendMutiToNoDlq(messageTestReq.getMessage()));
    }
}
