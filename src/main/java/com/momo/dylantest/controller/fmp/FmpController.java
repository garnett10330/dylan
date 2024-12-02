package com.momo.dylantest.controller.fmp;

import com.momo.dylantest.model.PageVo;
import com.momo.dylantest.model.dto.CompanyStockDto;
import com.momo.dylantest.model.request.BasePageReq;
import com.momo.dylantest.model.request.FmpSaveAllReq;
import com.momo.dylantest.model.request.StockSymbolSearchReq;
import com.momo.dylantest.response.Response;
import com.momo.dylantest.service.fmp.CompanyStockService;
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
     * @param fmpSaveAllReq 包含 companyName 請求參數 {@code FmpSaveAllReq}。
     * @return 保存操作的結果。{@link Response}
     * @throws Exception 如果保存過程中發生錯誤。
     */
    @PostMapping("/batch/mysql/v1")
    public Response<String> saveAllMysql(@Valid @RequestBody FmpSaveAllReq fmpSaveAllReq) throws Exception{
        companyStockService.insertBatchForMysql(fmpSaveAllReq.getCompanyName());
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
    @GetMapping("/mysql/v1")
    public Response<PageVo<CompanyStockDto>> getStocksMysql(BasePageReq req) {
        return Response.success(companyStockService.getAllStocksWithPageMysql(req));
    }
    /**
     * 批量保存公司股票數據到 PostgreSQL。
     * <p>
     * 根據提供的公司名稱(like%)，批量將相關的股票數據保存到 PostgreSQL。
     * </p>
     * @param fmpSaveAllReq 包含 companyName 請求參數 {@code FmpSaveAllReq}。
     * @return 保存操作的結果。{@link Response}
     * @throws Exception 如果保存過程中發生錯誤。
     */
    @PostMapping("/batch/postgres/v1")
    public Response<String> saveAllPostgres(@Valid @RequestBody FmpSaveAllReq fmpSaveAllReq) throws Exception{
        companyStockService.insertBatchForPostgres(fmpSaveAllReq.getCompanyName());
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
    @PostMapping("/search/v1")
    public Response<PageVo<CompanyStockDto>> searchStocks(@Valid @RequestBody StockSymbolSearchReq req) {
        return Response.success(companyStockService.findStocksBySymbolWithPage(req));
    }

}
