package com.momo.dylantest.service;

import com.momo.dylantest.api.FmpApi;
import com.momo.dylantest.model.PageResult;
import com.momo.dylantest.model.dto.CompanyStockDto;
import com.momo.dylantest.model.dto.api.CompanyStockApiDto;
import com.momo.dylantest.model.mysql.CompanyStockMysqlPo;
import com.momo.dylantest.model.postgres.CompanyStockPostgresPo;
import com.momo.dylantest.repository.mysql.CompanyStockMysqlRepository;
import com.momo.dylantest.repository.postgres.CompanyStockPostgresRepository;
import com.momo.dylantest.util.LogUtil;
import com.momo.dylantest.util.PageConverter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private CompanyStockMysqlRepository companyStockMysqlRepository;
    @Resource
    private CompanyStockPostgresRepository companyStockPostgresRepository;
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
        long count = companyStockMysqlRepository.count();
        long insertStart = System.currentTimeMillis();
        companyStockMysqlRepository.insertBatchFromJdbc(listAll);
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
        Pageable pageable = PageRequest.of(page, size); // 定義分頁規則
        Page<CompanyStockDto> stockPage  = companyStockMysqlRepository.findAllDto(pageable);
        int totalPages = stockPage.getTotalPages();   // 總頁數
        long totalElements = stockPage.getTotalElements(); // 總記錄數
        log.info(LogUtil.info(2,"getAllStocksWithPageMysql",
                "totalPages:"+totalPages+",totalElements:"+totalElements+",size:"+stockPage.getSize()));
        return PageConverter.convertToPageResult(stockPage); // 查詢所有公司股票並進行分頁
    }
    /**
     * 批量插入公司股票數據到 PostgreSQL。
     *
     * @param companyName 要搜索的公司名稱。
     * @throws Exception 如果插入過程中發生任何錯誤。
     */
    @Transactional(rollbackFor = Exception.class,value= "postgresTransactionManager")
    public void insertBatch(String companyName) throws Exception{
        long start = System.currentTimeMillis();
        List<CompanyStockApiDto> list = fmpApi.searchCompany(companyName);
        long searchCompanyEnd = System.currentTimeMillis();
        List<CompanyStockPostgresPo> listAll = list.parallelStream().map(CompanyStockPostgresPo::new).toList();
        long count = companyStockPostgresRepository.count();
        long insertBatchStart = System.currentTimeMillis();
        companyStockPostgresRepository.insertBatch(listAll);
        long insertBatchEnd = System.currentTimeMillis();
        log.info(LogUtil.info(LogUtil.GATE_FRONT,"insertBatch","searchCompany消耗時間:"+(searchCompanyEnd-start)+",insert前的count:"+count
                +",jpaInsertBatch消耗時間:"+(insertBatchEnd-insertBatchStart)+",筆數:"+listAll.size()));
//        Assert.isTrue(false,"測試");
        companyStockPostgresRepository.insertBatchFromJdbc(listAll);
        long end = System.currentTimeMillis();
        log.info(LogUtil.info(LogUtil.GATE_FRONT,"postgresInsertBatchFromJdbc","消耗時間:"+(end-insertBatchEnd)));
    }
    /**
     * 從 PostgreSQL 中以分頁方式檢索所有公司股票數據。
     *
     * @param page 要檢索的頁碼。
     * @param size 每頁的大小。
     * @return 包含分頁公司股票數據的 {@link PageResult<CompanyStockDto>}。
     */
    public PageResult<CompanyStockDto> getAllStocksWithPagePostgres(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // 定義分頁規則
        Page<CompanyStockDto> stockPage  = companyStockPostgresRepository.findAllDto(pageable);
        int totalPages = stockPage.getTotalPages();   // 總頁數
        long totalElements = stockPage.getTotalElements(); // 總記錄數
        log.info(LogUtil.info(2,"getAllStocksWithPagePostgres",
                "totalPages:"+totalPages+",totalElements:"+totalElements+",size:"+stockPage.getSize()));
        return PageConverter.convertToPageResult(stockPage); // 查詢所有公司股票並進行分頁
    }
}
