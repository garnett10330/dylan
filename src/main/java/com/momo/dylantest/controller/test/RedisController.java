package com.momo.dylantest.controller.test;

import com.momo.dylantest.model.mysql.CompanyStockMysqlPo;
import com.momo.dylantest.model.request.RedisSetReq;
import com.momo.dylantest.response.Response;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
/**
 * redis 測試控制器。
 *
 * 該控制器負責與 redis 的交互，提供set get 功能。
 */
@RestController
@RequestMapping("/api/test")
public class RedisController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 向 Redis 中設置一個對象值。
     * <p>
     * 該方法使用指定的 key 和 data 創建一個 {@link CompanyStockMysqlPo} 對象，
     * 並將該對象存儲到 Redis 中，有效期為 100 秒。
     * </p>
     * @param redisSetReq 包含查詢條件的請求物件，其中包括：
     *            <ul>
     *                <li>{@code key}  存儲的鍵（必選）。</li>
     *                <li>{@code data} 存儲的數據，用於填充對象的 symbol 字段。（必選）。</li>
     *            </ul>
     * @return 包裝在 {@link Response} 中的成功消息，內容為固定字符串 "Value set in Redis"。
     */
    @PostMapping("/redis/object/v1")
    public Response<String> setValue(@Valid @RequestBody RedisSetReq redisSetReq) {
        CompanyStockMysqlPo companyStock = new CompanyStockMysqlPo();
        companyStock.setId(12345);
        companyStock.setGmtCreated(LocalDateTime.now());
        companyStock.setSymbol(redisSetReq.getData());
        redisTemplate.opsForValue().set(redisSetReq.getKey(), companyStock,100, TimeUnit.SECONDS);
        return Response.success("Value set in Redis");
    }

    /**
     * 從 Redis 中獲取對象值。
     * <p>
     * 該方法使用指定的 key 從 Redis 中檢索存儲的對象值。
     * 如果鍵不存在，返回值中的數據部分為 {@code null}。
     * </p>
     *
     * @param key 存儲的鍵，用於檢索對象。
     * @return 包裝在 {@link Response} 中的對象值：
     *         <ul>
     *             <li>當鍵存在時，返回存儲的對象，類型為動態類型。</li>
     *             <li>當鍵不存在時，返回 {@code null}。</li>
     *         </ul>
     */
    @GetMapping("/redis/object/v1")
    public Response<Object> getObjectValue(@RequestParam String key) {
        return Response.success(redisTemplate.opsForValue().get(key));
    }
}
