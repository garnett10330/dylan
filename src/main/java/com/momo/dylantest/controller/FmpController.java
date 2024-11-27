package com.momo.dylantest.controller;

import com.momo.dylantest.model.PageVo;
import com.momo.dylantest.model.dto.CompanyStockDto;
import com.momo.dylantest.model.request.BasePageReq;
import com.momo.dylantest.model.request.StockSymbolSearchReq;
import com.momo.dylantest.response.Response;
import com.momo.dylantest.service.CompanyStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
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
    public Response<String> saveAllMysql(@RequestParam String companyName) throws Exception{
        companyStockService.insertBatchForMysql(companyName);
        return Response.success();
    }
    /**
     * 分頁獲取 MySQL 中的公司股票數據。
     *
     * @param req 包含分頁信息的請求物件 {@link BasePageReq}，其中包含以下內容：
     *            <ul>
     *                <li>page - 請求的頁碼（從 1 開始）。</li>
     *                <li>size - 每頁顯示的數據條數。</li>
     *            </ul>
     * @return 包含公司股票數據的分頁結果。
     * 返回值為 {@link Response}，其中封裝了 {@link PageVo}。
     *  *         {@link PageVo} 包含分頁信息和 {@link CompanyStockDto} 的數據列表。
     */
    @Operation(summary = "分頁獲取 mysql 中的公司股票數據",
            description = "根據頁碼與頁大小，分頁查詢 PostgreSQL 中的公司股票數據。",
            parameters = {
                    @Parameter(name = "pageNum", description = "當前頁碼，默認為 1", required = false),
                    @Parameter(name = "pageSize", description = "每頁大小，默認為 10", required = false)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "查詢成功",
                            content = @Content(schema = @Schema(implementation = PageVo.class))),
                    @ApiResponse(responseCode = "400", description = "參數錯誤",
                            content = @Content),
                    @ApiResponse(responseCode = "500", description = "伺服器錯誤",
                            content = @Content)
            })
    @GetMapping("/mysql")
    public Response<PageVo<CompanyStockDto>> getStocksMysql(BasePageReq req) {
        return Response.success(companyStockService.getAllStocksWithPageMysql(req));
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
    public Response<String> saveAllPostgres(@RequestParam String companyName) throws Exception{
        companyStockService.insertBatchForPostgres(companyName);
        return Response.success();
    }
    /**
     * 分頁獲取 PostgreSQL 中的公司股票數據。
     *
     * @param req 包含分頁信息的請求物件 {@link BasePageReq}，其中包含以下內容：
     *            <ul>
     *                <li>page - 請求的頁碼（從 1 開始）。</li>
     *                <li>size - 每頁顯示的數據條數。</li>
     *            </ul>
     * @return 包含公司股票數據的分頁結果。
     * 返回值為 {@link Response}，其中封裝了 {@link PageVo}。
     *  *         {@link PageVo} 包含分頁信息和 {@link CompanyStockDto} 的數據列表。
     */
    @Operation(summary = "分頁獲取 PostgreSQL 中的公司股票數據",
            description = "根據頁碼與頁大小，分頁查詢 PostgreSQL 中的公司股票數據。",
            parameters = {
                    @Parameter(name = "pageNum", description = "當前頁碼，默認為 1", required = false),
                    @Parameter(name = "pageSize", description = "每頁大小，默認為 10", required = false)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "查詢成功",
                            content = @Content(schema = @Schema(implementation = PageVo.class))),
                    @ApiResponse(responseCode = "400", description = "參數錯誤",
                            content = @Content),
                    @ApiResponse(responseCode = "500", description = "伺服器錯誤",
                            content = @Content)
            })
    @GetMapping("/postgres")
    public Response<PageVo<CompanyStockDto>> getStocksPostgres(BasePageReq req) {
        return Response.success(companyStockService.getAllStocksWithPagePostgres(req));
    }


    /**
     * 根據股票代號（symbol）和分頁條件查詢公司股票數據。
     * <p>
     * 該方法接受 JSON 格式的查詢參數，支持根據股票代號進行模糊匹配，
     * 並按照分頁條件返回匹配的數據列表。
     * </p>
     *
     * @param req 包含 symbol、pageNum 和 pageSize 的請求參數 {@link StockSymbolSearchReq}。
     *            <ul>
     *                <li>{@code symbol} - 股票代號（必填）。</li>
     *                <li>{@code pageNum} - 頁碼（默認為 1）。</li>
     *                <li>{@code pageSize} - 每頁條數（默認為 10）。</li>
     *            </ul>
     * @return 包裝在 {@link PageVo} 中的分頁結果。
     *         返回值類型為 {@link Response}<{@link PageVo}<{@link CompanyStockDto}>>。
     */
    @Operation(
            summary = "查詢公司股票數據",
            description = "根據股票代號（symbol）和分頁條件查詢公司股票數據，symbol 不可為空。",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "包含股票代號（symbol）和分頁條件的查詢請求",
                    required = true,
                    content = @Content(schema = @Schema(implementation = StockSymbolSearchReq.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "查詢成功",
                            content = @Content(schema = @Schema(implementation = PageVo.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "參數錯誤"),
                    @ApiResponse(responseCode = "500", description = "伺服器錯誤")
            }
    )
    @PostMapping("/search")
    public Response<PageVo<CompanyStockDto>> searchStocks(@RequestBody @Valid StockSymbolSearchReq req) {
        return Response.success(companyStockService.findStocksBySymbolWithPage(req));
    }




}
