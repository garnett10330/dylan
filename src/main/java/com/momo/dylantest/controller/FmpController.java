package com.momo.dylantest.controller;

import com.momo.dylantest.model.PageResult;
import com.momo.dylantest.model.dto.CompanyStockDto;
import com.momo.dylantest.response.Response;
import com.momo.dylantest.service.CompanyStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
/**
 * 管理公司股票數據的批量操作與分頁查詢
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/v1/stocks")
@Tag(name = "公司股票管理", description = "提供批量保存與分頁查詢的 API")
public class FmpController {

    @Resource
    private CompanyStockService companyStockService;

    /**
     * 批量保存公司股票數據到 MySQL。
     *
     * @param companyName 要保存的公司名稱。
     * @return 保存操作的結果。{@link Response}
     * @throws Exception 如果保存過程中發生錯誤。
     */
    @Operation(summary = "批量保存公司股票數據到 MySQL",
            description = "根據提供的公司名稱，批量將相關的股票數據保存到 MySQL。",
            parameters = @Parameter(name = "companyName", description = "公司名稱", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "查詢成功", content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "500", description = "伺服器錯誤", content = @Content)
            })
    @PostMapping("/batch/mysql")
    public Response saveAllMysql(@RequestParam String companyName) throws Exception{
        companyStockService.insertBatchForMysql(companyName);
        return Response.success();
    }
    /**
     * 分頁獲取 MySQL 中的公司股票數據。
     *
     * @param page 當前頁碼，默認為 0。
     * @param size 每頁的大小，默認為 10。
     * @return 包含公司股票數據的分頁結果。{@link Response<com.momo.dylantest.model.PageResult<CompanyStockDto>>}
     */
    @Operation(summary = "分頁獲取 MySQL 中的公司股票數據",
            description = "根據頁碼與頁大小，分頁查詢 MySQL 中的公司股票數據。",
            parameters = {
                    @Parameter(name = "page", description = "當前頁碼，默認為 0", required = false),
                    @Parameter(name = "size", description = "每頁大小，默認為 10", required = false)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "查詢成功", content = @Content(schema = @Schema(implementation = PageResult.class))),
                    @ApiResponse(responseCode = "500", description = "伺服器錯誤", content = @Content)
            })
    @GetMapping("/mysql")
    public Response getStocksMysql(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        return Response.success(companyStockService.getAllStocksWithPageMysql(page, size));
    }
    /**
     * 批量保存公司股票數據到 PostgreSQL。
     *
     * @param companyName 要保存的公司名稱。
     * @return 保存操作的結果。{@link Response}
     * @throws Exception 如果保存過程中發生錯誤。
     */
    @Operation(summary = "批量保存公司股票數據到 PostgreSQL",
            description = "根據提供的公司名稱，批量將相關的股票數據保存到 PostgreSQL。",
            parameters = @Parameter(name = "companyName", description = "公司名稱", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "保存成功", content = @Content(schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "500", description = "伺服器錯誤", content = @Content)
            })
    @PostMapping("/batch/postgres")
    public Response saveAllPostgres(@RequestParam String companyName) throws Exception{
        companyStockService.insertBatchForPostgres(companyName);
        return Response.success();
    }
    /**
     * 分頁獲取 PostgreSQL 中的公司股票數據。
     *
     * @param page 當前頁碼，默認為 0。
     * @param size 每頁的大小，默認為 10。
     * @return 包含公司股票數據的分頁結果。{@link Response<com.momo.dylantest.model.PageResult<CompanyStockDto>>}
     */
    @Operation(summary = "分頁獲取 PostgreSQL 中的公司股票數據",
            description = "根據頁碼與頁大小，分頁查詢 PostgreSQL 中的公司股票數據。",
            parameters = {
                    @Parameter(name = "page", description = "當前頁碼，默認為 0", required = false),
                    @Parameter(name = "size", description = "每頁大小，默認為 10", required = false)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "查詢成功", content = @Content(schema = @Schema(implementation = PageResult.class))),
                    @ApiResponse(responseCode = "500", description = "伺服器錯誤", content = @Content)
            })
    @GetMapping("/postgres")
    public Response getStocksPostgres(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return Response.success(companyStockService.getAllStocksWithPagePostgres(page, size));
    }




}
