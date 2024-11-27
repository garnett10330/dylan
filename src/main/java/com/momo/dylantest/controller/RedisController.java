package com.momo.dylantest.controller;

import com.momo.dylantest.model.mysql.CompanyStockMysqlPo;
import com.momo.dylantest.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

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
     *
     * @param key  存儲的鍵。
     * @param data 存儲的數據，用於填充對象的 symbol 字段。
     * @return 包裝在 {@link Response} 中的成功消息，內容為固定字符串 "Value set in Redis"。
     */
    @Operation(
            summary = "設置 Redis 對象值",
            description = "使用指定的 key 和 data 將對象存儲到 Redis 中，有效期為 100 秒。",
            parameters = {
                    @Parameter(name = "key", description = "Redis 鍵值", required = true),
                    @Parameter(name = "data", description = "用於填充對象的數據", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "操作成功",
                            content = @Content(schema = @Schema(type = "string", example = "Value set in Redis"))),
                    @ApiResponse(responseCode = "400", description = "參數錯誤"),
                    @ApiResponse(responseCode = "500", description = "伺服器錯誤")
            }
    )
    @PostMapping("/redis/object/v1")
    public Response<String> setValue(@RequestParam String key, @RequestParam String data) {
        CompanyStockMysqlPo companyStock = new CompanyStockMysqlPo();
        companyStock.setId(12345);
        companyStock.setGmtCreated(LocalDateTime.now());
        companyStock.setSymbol(data);
        redisTemplate.opsForValue().set(key, companyStock,100, TimeUnit.SECONDS);
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
    @Operation(
            summary = "獲取 Redis 對象值",
            description = "根據指定的 key 從 Redis 中檢索對象值。返回的對象類型是動態的，具體取決於存儲的數據。",
            parameters = @Parameter(name = "key", description = "Redis 鍵值", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "操作成功",
                            content = @Content(schema = @Schema(type = "object"))),
                    @ApiResponse(responseCode = "400", description = "參數錯誤"),
                    @ApiResponse(responseCode = "404", description = "對象不存在",
                            content = @Content(schema = @Schema(type = "string", example = "null"))),
                    @ApiResponse(responseCode = "500", description = "伺服器錯誤")
            }
    )
    @GetMapping("/redis/object/v1")
    public Response<Object> getObjectValue(@RequestParam String key) {
        return Response.success(redisTemplate.opsForValue().get(key));
    }
}
