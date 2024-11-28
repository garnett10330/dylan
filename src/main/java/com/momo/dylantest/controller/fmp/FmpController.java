package com.momo.dylantest.controller.fmp;

import com.momo.dylantest.model.PageVo;
import com.momo.dylantest.model.dto.CompanyStockDto;
import com.momo.dylantest.model.request.BasePageReq;
import com.momo.dylantest.model.request.StockSymbolSearchReq;
import com.momo.dylantest.response.Response;
import com.momo.dylantest.response.swagger.ErrorResponse;
import com.momo.dylantest.service.CompanyStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
/**
 * stocks 控制器。
 * 管理公司股票數據的批量操作與分頁查詢
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/api/stocks")
public class FmpController {

    @Resource
    private CompanyStockService companyStockService;

    /**
     * 批量保存公司股票數據到 MySQL。
     * <p>
     * 根據提供的公司名稱(like%)，批量將相關的股票數據保存到 MySQL。
     * </p>
     * @param companyName 要保存的公司名稱。
     * @return 保存操作的結果。{@link Response}
     * @throws Exception 如果保存過程中發生錯誤。
     */
    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "500", description = "ERROR",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    @PostMapping("/batch/mysql/v1")
    public Response<String> saveAllMysql(@RequestParam String companyName) throws Exception{
        companyStockService.insertBatchForMysql(companyName);
        return Response.success();
    }
    /**
     * 分頁獲取 MySQL 中的公司股票數據。
     * @param req 包含分頁信息的請求物件 {@link BasePageReq}，其中包含以下內容：
     *            <ul>
     *                <li>page - 請求的頁碼（從 1 開始）。</li>
     *                <li>size - 每頁顯示的數據條數。</li>
     *            </ul>
     * @return 包含公司股票數據的分頁結果。
     * 返回值為 {@link Response}，其中封裝了 {@link PageVo}。
     *  *         {@link PageVo} 包含分頁信息和 {@link CompanyStockDto} 的數據列表。
     */
    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "500", description = "ERROR",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    @GetMapping("/mysql/v1")
    public Response<PageVo<CompanyStockDto>> getStocksMysql(BasePageReq req) {
        return Response.success(companyStockService.getAllStocksWithPageMysql(req));
    }
    /**
     * 批量保存公司股票數據到 PostgreSQL。
     * <p>
     * 根據提供的公司名稱(like%)，批量將相關的股票數據保存到 PostgreSQL。
     * </p>
     * @param companyName 要保存的公司名稱。
     * @return 保存操作的結果。{@link Response}
     * @throws Exception 如果保存過程中發生錯誤。
     */
    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "500", description = "ERROR",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    @PostMapping("/batch/postgres/v1")
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
     */
    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "500", description = "ERROR",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    @GetMapping("/postgres/v1")
    public Response<PageVo<CompanyStockDto>> getStocksPostgres(BasePageReq req) {
        return Response.success(companyStockService.getAllStocksWithPagePostgres(req));
    }


    /**
     *
     * 根據股票代號（symbol）和分頁條件查詢公司股票數據。
     *
     * <p>
     * 該方法接受 JSON 格式的查詢參數，支持根據股票代號進行模糊匹配，
     * 並按照分頁條件返回匹配的數據列表。
     * </p>
     *
     * @param req 包含 symbol、pageNum 和 pageSize 的請求參數 {@code StockSymbolSearchReq}。
     * @return 分頁查詢結果
     */
    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "500", description = "ERROR",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    @PostMapping("/search/v1")
    public Response<PageVo<CompanyStockDto>> searchStocks(@RequestBody @Valid StockSymbolSearchReq req) {
        return Response.success(companyStockService.findStocksBySymbolWithPage(req));
    }

}
