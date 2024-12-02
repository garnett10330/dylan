package com.momo.dylantest.service.fmp;

import com.momo.dylantest.api.FmpApi;
import com.momo.dylantest.mapper.mysql.MysqlDBMapper;
import com.momo.dylantest.mapper.postgres.PostgresDBMapper;
import com.momo.dylantest.model.PageVo;
import com.momo.dylantest.model.dto.CompanyStockDto;
import com.momo.dylantest.model.dto.api.CompanyStockApiDto;
import com.momo.dylantest.model.mysql.CompanyStockMysqlPo;
import com.momo.dylantest.model.postgres.CompanyStockPostgresPo;
import com.momo.dylantest.model.request.BasePageReq;
import com.momo.dylantest.model.request.StockSymbolSearchReq;
import com.momo.dylantest.util.LogUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
/**
 * 管理公司股票數據的服務類。
 *
 * 此服務類提供與存儲在 MySQL 和 PostgreSQL 中的公司股票數據交互的方法。
 * 包括批量插入數據和檢索分頁結果的操作。
 */
@Validated
@Service
@Slf4j
public class CompanyStockService {
    @Resource
    private FmpApi fmpApi;
    @Resource
    private PostgresDBMapper postgresDBMapper;
    @Resource
    private MysqlDBMapper mysqlDBMapper;
    /**
     * 批量插入公司股票數據到 MySQL。
     *
     * @param companyName 要搜索的公司名稱。
     * @throws Exception 如果插入過程中發生任何錯誤。
     */
    @Transactional(rollbackFor = Exception.class,value= "mysqlTransactionManager")
    public void insertBatchForMysql(String companyName) throws Exception{
        long start = System.currentTimeMillis();
        List<CompanyStockApiDto> list = fmpApi.searchCompany(companyName);
        List<CompanyStockMysqlPo> listAll = list.parallelStream().map(CompanyStockMysqlPo::new).toList();
        long count = mysqlDBMapper.findCompanyStockCount();
        long insertStart = System.currentTimeMillis();
        mysqlDBMapper.insertBatchCompanyStock(listAll);
        long end = System.currentTimeMillis();
        log.info(LogUtil.info(LogUtil.GATE_FRONT,"insertBatchForMysql","searchCompany消耗時間:"+(insertStart-start)+",count:"+count+",insertBatch消耗時間:"+(end-insertStart)+",筆數:"+listAll.size()));
    }
    /**
     * 從 MySQL 中以分頁方式檢索所有公司股票數據。
     * <p>
     * 該方法根據分頁參數檢索 MySQL 數據庫中的公司股票數據，
     * 並返回分頁結果及總記錄數。
     * </p>
     *
     * @param req 包含分頁信息的請求物件 {@link BasePageReq}，其中包括：
     *            <ul>
     *                <li>{@code pageNum} - 請求的頁碼（從 1 開始）。</li>
     *                <li>{@code pageSize} - 每頁顯示的數據條數。</li>
     *            </ul>
     * @return 包裝在 {@link PageVo} 中的分頁結果，包含：
     *         <ul>
     *             <li>數據列表（{@link List}<{@link CompanyStockDto}>）。</li>
     *             <li>總記錄數。</li>
     *         </ul>
     */
    public PageVo<CompanyStockDto> getAllStocksWithPageMysql(BasePageReq req) {
        List<CompanyStockDto> stockList  = mysqlDBMapper.findCompanyStockPage(req.getOffset() ,req.getPageSize());
        long total = mysqlDBMapper.findCompanyStockCount(); // 總記錄數
        log.info(LogUtil.info(2,"getAllStocksWithPageMysql",
                ",total:"+total+",stockList size:"+stockList.size()));
        return new PageVo<>(stockList,total); // 查詢所有公司股票並進行分頁
    }
    /**
     * 批量插入公司股票數據到 PostgreSQL。
     *
     * @param companyName 要搜索的公司名稱。
     * @throws Exception 如果插入過程中發生任何錯誤。
     */
    @Transactional(rollbackFor = Exception.class,value= "postgresTransactionManager")
    public void insertBatchForPostgres(String companyName) throws Exception{
        long start = System.currentTimeMillis();
        List<CompanyStockApiDto> list = fmpApi.searchCompany(companyName);
        List<CompanyStockPostgresPo> listAll = list.parallelStream().map(CompanyStockPostgresPo::new).toList();
        long count = postgresDBMapper.findCompanyStockCount();
        long insertStart = System.currentTimeMillis();
        postgresDBMapper.insertBatchCompanyStock(listAll);
        long end = System.currentTimeMillis();
        log.info(LogUtil.info(LogUtil.GATE_FRONT,"insertBatchForMysql","searchCompany消耗時間:"+(insertStart-start)+",count:"+count+",insertBatch消耗時間:"+(end-insertStart)+",筆數:"+listAll.size()));
    }
    /**
     * 從 PostgreSQL 中以分頁方式檢索所有公司股票數據。
     * <p>
     * 該方法根據分頁參數檢索 PostgreSQL 數據庫中的公司股票數據，
     * 並返回分頁結果及總記錄數。
     * </p>
     *
     * @param req 包含分頁信息的請求物件 {@link BasePageReq}，其中包括：
     *            <ul>
     *                <li>{@code pageNum} - 請求的頁碼（從 1 開始）。</li>
     *                <li>{@code pageSize} - 每頁顯示的數據條數。</li>
     *            </ul>
     * @return 包裝在 {@link PageVo} 中的分頁結果，包含：
     *         <ul>
     *             <li>數據列表（{@link List}<{@link CompanyStockDto}>）。</li>
     *             <li>總記錄數。</li>
     *         </ul>
     */
    public PageVo<CompanyStockDto> getAllStocksWithPagePostgres(BasePageReq req) {
        List<CompanyStockDto> stockList  = postgresDBMapper.findCompanyStockPage(req.getOffset() ,req.getPageSize());
        long total = postgresDBMapper.findCompanyStockCount(); // 總記錄數
        log.info(LogUtil.info(2,"getAllStocksWithPageMysql",
                ",total:"+total+",stockList size:"+stockList.size()));
        return new PageVo<>(stockList,total); // 查詢所有公司股票並進行分頁
    }


    /**
     * 根據股票代號（symbol）和分頁條件查詢公司股票數據。
     * 該方法接受包含分頁條件的請求物件 {@link StockSymbolSearchReq}，
     * 根據指定的股票代號（symbol）進行模糊查詢，並返回分頁結果。
     * @param req 包含查詢條件的請求對象 {@link StockSymbolSearchReq}，其中包括：
     *            <ul>
     *                <li>{@code symbol} - 股票代號（必填）。</li>
     *                <li>{@code pageNum} - 頁碼（默認為 1）。</li>
     *                <li>{@code pageSize} - 每頁條數（默認為 10）。</li>
     *            </ul>
     * @return 包裝在 {@link PageVo} 中的分頁結果。
     *         <ul>
     *             <li>{@code data} - 當前頁的公司股票數據列表，類型為 {@link List}<{@link CompanyStockDto}>。</li>
     *             <li>{@code total} - 符合條件的數據總數。</li>
     *         </ul>
     */
    public PageVo<CompanyStockDto> findStocksBySymbolWithPage(StockSymbolSearchReq req) {
        String symbol= req.getSymbol();
        List<CompanyStockDto> stockList = mysqlDBMapper.findBySymbolWithPage(symbol, req.getOffset(), req.getPageSize());
        long total = mysqlDBMapper.countBySymbol(symbol);
        return new PageVo<>(stockList, total);
    }
}
