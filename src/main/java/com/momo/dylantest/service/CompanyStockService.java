package com.momo.dylantest.service;

import com.momo.dylantest.api.FmpApi;
import com.momo.dylantest.mapper.mysql.MysqlDBMapper;
import com.momo.dylantest.mapper.postgres.PostgresDBMapper;
import com.momo.dylantest.model.PageResult;
import com.momo.dylantest.model.dto.CompanyStockDto;
import com.momo.dylantest.model.dto.api.CompanyStockApiDto;
import com.momo.dylantest.model.mysql.CompanyStockMysqlPo;
import com.momo.dylantest.model.postgres.CompanyStockPostgresPo;
import com.momo.dylantest.util.LogUtil;
import com.momo.dylantest.util.PageConverter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;
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
        List<CompanyStockMysqlPo> listAll = list.parallelStream().map(companyStock -> new CompanyStockMysqlPo(companyStock)).collect(Collectors.toList());
        long count = mysqlDBMapper.findCompanyStockCount();
        long insertStart = System.currentTimeMillis();
        mysqlDBMapper.insertBatchCompanyStock(listAll);
        long end = System.currentTimeMillis();
        log.info(LogUtil.info(LogUtil.GATE_FRONT,"insertBatchForMysql","searchCompany消耗時間:"+(insertStart-start)+",count:"+count+",insertBatch消耗時間:"+(end-insertStart)+",筆數:"+listAll.size()));
    }
    /**
     * 從 MySQL 中以分頁方式檢索所有公司股票數據。
     *
     * @param page 要檢索的頁碼。
     * @param size 每頁的大小。
     * @return 包含分頁公司股票數據的 {@link PageResult}。
     */
    public PageResult<CompanyStockDto> getAllStocksWithPageMysql(int page, int size) {
        //Pageable pageable = PageRequest.of(page, size); // 定義分頁規則
        List<CompanyStockDto> stockPage  = mysqlDBMapper.findCompanyStockPage((page+1)*size ,size);
        long totalElement = mysqlDBMapper.findCompanyStockCount(); // 總記錄數
        long totalPages = totalElement/size;   // 總頁數
        log.info(LogUtil.info(2,"getAllStocksWithPageMysql",
                "totalPages:"+totalPages+",totalElements:"+totalElement+",size:"+size));
        return PageConverter.convertToPageResult(stockPage,  totalElement,  (int)totalPages,  page,  size); // 查詢所有公司股票並進行分頁
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
        List<CompanyStockPostgresPo> listAll = list.parallelStream().map(companyStock -> new CompanyStockPostgresPo(companyStock)).collect(Collectors.toList());
        long count = postgresDBMapper.findCompanyStockCount();
        long insertStart = System.currentTimeMillis();
        postgresDBMapper.insertBatchCompanyStock(listAll);
        long end = System.currentTimeMillis();
        log.info(LogUtil.info(LogUtil.GATE_FRONT,"insertBatchForMysql","searchCompany消耗時間:"+(insertStart-start)+",count:"+count+",insertBatch消耗時間:"+(end-insertStart)+",筆數:"+listAll.size()));
    }
    /**
     * 從 PostgreSQL 中以分頁方式檢索所有公司股票數據。
     *
     * @param page 要檢索的頁碼。
     * @param size 每頁的大小。
     * @return 包含分頁公司股票數據的 {@link PageResult<CompanyStockDto>}。
     */
    public PageResult<CompanyStockDto> getAllStocksWithPagePostgres(int page, int size) {
        List<CompanyStockDto> stockPage  = postgresDBMapper.findCompanyStockPage((page+1)*size ,size);
        long totalElement = postgresDBMapper.findCompanyStockCount(); // 總記錄數
        long totalPages = totalElement/size;   // 總頁數
        log.info(LogUtil.info(2,"getAllStocksWithPageMysql",
                "totalPages:"+totalPages+",totalElements:"+totalElement+",size:"+size));
        return PageConverter.convertToPageResult(stockPage,  totalElement,  (int)totalPages,  page,  size); // 查詢所有公司股票並進行分頁
    }
}
