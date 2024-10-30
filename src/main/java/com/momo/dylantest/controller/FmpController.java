package com.momo.dylantest.controller;

import com.momo.dylantest.model.CompanyStock;
import com.momo.dylantest.response.Response;
import com.momo.dylantest.service.CompanyStockService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@Slf4j
@RestController
@RequestMapping("/fmp")
public class FmpController {

    @Resource
    private CompanyStockService companyStockService;


    @GetMapping("/insertFmpCompany")
    public Response insertFmpCompany(@RequestParam String companyName){
        return Response.success(companyStockService.insertFmpCompany(companyName));
    }

    @GetMapping("/saveAll")
    public Response saveAll(@RequestParam String companyName) throws Exception{
        return Response.success(companyStockService.insertBatchHighPerformance(companyName));
    }

    @GetMapping("/stocks")
    public Page<CompanyStock> getStocks(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return companyStockService.getAllStocksWithPage(page, size);
    }




}
