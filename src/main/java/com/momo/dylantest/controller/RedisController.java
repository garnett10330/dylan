package com.momo.dylantest.controller;

import com.momo.dylantest.model.mysql.CompanyStockMysqlPo;
import com.momo.dylantest.response.Response;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/v1/test")
public class RedisController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @PostMapping("/redis/object")
    public Response setValue(@RequestParam String key, @RequestParam String data) {
        CompanyStockMysqlPo companyStock = new CompanyStockMysqlPo();
        companyStock.setId(12345);
        companyStock.setGmtCreated(LocalDateTime.now());
        companyStock.setSymbol(data);
        redisTemplate.opsForValue().set(key, companyStock,100, TimeUnit.SECONDS);
        return Response.success("Value set in Redis");
    }
    @GetMapping("/redis/object")
    public Response getObjectValue(@RequestParam String key) {
        return Response.success(redisTemplate.opsForValue().get(key));
    }
}
