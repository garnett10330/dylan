package com.momo.dylantest.controller;

import com.momo.dylantest.model.CompanyStock;
import com.momo.dylantest.response.Response;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class RedisController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @GetMapping("/set")
    public String setValue() {
        redisTemplate.opsForValue().set("key", "value");
        return "Value set in Redis";
    }

    @GetMapping("/get")
    public String getValue() {
        return (String) redisTemplate.opsForValue().get("key");
    }

    @GetMapping("/setObject")
    public Response setValue(@RequestParam String key, @RequestParam String data) {
        CompanyStock companyStock = new CompanyStock();
        companyStock.setId(12345);
        companyStock.setGmtCreated(LocalDateTime.now());
        companyStock.setSymbol(data);
        redisTemplate.opsForValue().set(key, companyStock);
        return Response.success("Value set in Redis");
    }
    @GetMapping("/getObject")
    public Response getObjectValue(@RequestParam String key) {
        return Response.success(redisTemplate.opsForValue().get(key));
    }
}
