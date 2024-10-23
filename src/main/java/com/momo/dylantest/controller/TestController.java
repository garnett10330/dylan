package com.momo.dylantest.controller;

import com.momo.dylantest.response.Response;
import com.momo.dylantest.service.P3reportWeeklyResponseRatioService;
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
@RequestMapping("/test")
public class TestController {

    @Resource
    private P3reportWeeklyResponseRatioService p3reportWeeklyResponseRatioService;

    @GetMapping("/findAll")
    public Response findAll(){
        return Response.success(p3reportWeeklyResponseRatioService.findAll());
    }
    @GetMapping("/findByTicketId")
    public Response findByTicketId(@RequestParam String ticketId){
        return Response.success(p3reportWeeklyResponseRatioService.findByTicketId(ticketId));
    }
    @GetMapping("/findByTicketIdJPQL")
    public Response findByTicketIdJPQL(
            @Pattern(regexp="^[0-9]{10}$", message="ticketId必須是十位數的格式")
            @RequestParam String ticketId){
        return Response.success(p3reportWeeklyResponseRatioService.findByTicketIdJPQL(ticketId));
    }
    @GetMapping("/findByConditions")
    public Response findByConditions(@RequestParam(required = false) String entpCode,@RequestParam(required = false) String goodsCode,@RequestParam(required = false) Boolean eUnread){
        return Response.success(p3reportWeeklyResponseRatioService.findByConditions(entpCode,goodsCode,eUnread));
    }


    @GetMapping("/testStr")
    public Response testStr(){
        return Response.success("test success");
    }



}
