package com.momo.dylantest.controller;//package com.momo.dylantest.controller;

import com.momo.dylantest.model.CompanyStock;
import com.momo.dylantest.response.Response;
import com.momo.dylantest.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class MessageProducerController {
    @Resource
    private MessageService messageService;

    @RequestMapping("/sendToNormal")
    public Response sendToNormal(@RequestParam String message) {
        CompanyStock companyStock = new CompanyStock();
        companyStock.setId(12345);
        companyStock.setGmtCreated(LocalDateTime.now());
        companyStock.setSymbol(message);
        return Response.success(messageService.sendToNormal(companyStock.toString()));
    }

    @RequestMapping("/sendToNoDlq")
    public Response sendToNoDlq(@RequestParam String message) {
        return Response.success(messageService.sendToNoDlq(message));
    }

}
