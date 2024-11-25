package com.momo.dylantest.controller;//package com.momo.dylantest.controller;

import com.momo.dylantest.model.mysql.CompanyStockMysqlPo;
import com.momo.dylantest.response.Response;
import com.momo.dylantest.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
/**
 * 消息生產者控制器。
 * 測試使用
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
    @PostMapping("/queue/normal")
    public Response sendToNormal(@RequestParam String message) {
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
    @PostMapping("/queue/no-dlq")
    public Response sendToNoDlq(@RequestParam String message) {
        return Response.success(messageService.sendToNoDlq(message));
    }
    /**
     * 批量發送消息到無死信佇列。
     *
     * @param message 要發送的消息內容，每條消息將附加唯一的識別符。
     * @return 成功發送批量消息的回應。
     */
    @PostMapping("/queue/no-dlq/batch")
    public Response sendMutiToNoDlq(@RequestParam String message) {
        return Response.success(messageService.sendMutiToNoDlq(message));
    }

}
