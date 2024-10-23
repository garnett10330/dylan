package com.momo.dylantest.service;

import com.momo.dylantest.api.FmpApi;
import com.momo.dylantest.model.CompanyStock;
import com.momo.dylantest.repository.CompanyStockRepository;
import com.momo.dylantest.util.LogUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

@Validated
@Service
@Slf4j
public class CompanyStockService {
    @Resource
    private FmpApi fmpApi;
    @Resource
    private CompanyStockRepository companyStockRepository;

    @Transactional(rollbackFor = Exception.class)
    public long insertFmpCompany(String companyName){
        long starts = System.currentTimeMillis();
        List<CompanyStock> listAll = new ArrayList<>();
        List<CompanyStock> list = fmpApi.searchCompany(companyName);
        for(int i =0;i < 100;i++){
            listAll.addAll(list);
        }
        long count = companyStockRepository.count();
        long start = System.currentTimeMillis();
        companyStockRepository.batchInsertJdbc(listAll);
        long end = System.currentTimeMillis();
        log.info(LogUtil.info(LogUtil.GATE_FRONT,"batchInsertJdbc","searchCompany消耗時間:"+(start-starts)+",count:"+count+",batchInsertJdbc消耗時間:"+(end-start)+",比數:"+listAll.size()));
        return (end-start);
    }
    @Transactional(rollbackFor = Exception.class)
    public long testHighPerformanceBatchInsert(String companyName) throws Exception{
        long starts = System.currentTimeMillis();
        List<CompanyStock> listAll = new ArrayList<>();
        List<CompanyStock> list = fmpApi.searchCompany(companyName);
        for(int i =0;i < 100;i++){
            listAll.addAll(list);
        }
        long count = companyStockRepository.count();
        long start = System.currentTimeMillis();
        companyStockRepository.highPerformanceBatchInsert(listAll);
        long end = System.currentTimeMillis();
        log.info(LogUtil.info(LogUtil.GATE_FRONT,"highPerformanceBatchInsert","searchCompany消耗時間:"+(start-starts)+",count:"+count+",highPerformanceBatchInsert消耗時間:"+(end-start)+",比數:"+listAll.size()));
        return (end-start);
    }
    public Page<CompanyStock> getAllStocksWithPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // 定義分頁規則
        Page<CompanyStock> stockPage  = companyStockRepository.findAll(pageable);
        int totalPages = stockPage.getTotalPages();   // 總頁數
        long totalElements = stockPage.getTotalElements(); // 總記錄數
        log.info(LogUtil.info(2,"getAllStocksWithPage",
                "totalPages:"+totalPages+",totalElements:"+totalElements+",size:"+stockPage.getSize()));
        return stockPage; // 查詢所有公司股票並進行分頁
    }
}
