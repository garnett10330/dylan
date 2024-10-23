package com.momo.dylantest.repository;

import com.momo.dylantest.model.CompanyStock;
import com.momo.dylantest.repository.custom.customCompanyStock.CustomCompanyStockRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyStockRepository extends JpaRepository<CompanyStock, Integer>, CustomCompanyStockRepository {
    @Query(value = "SELECT * FROM company_stock WHERE exchange_short_name = :exchangeShortName", nativeQuery = true)
    List<CompanyStock> findByExchangeShortName(@Param("exchangeShortName") String exchangeShortName);
    @Query("SELECT w FROM CompanyStock w WHERE w.currency = :currency")
    List<CompanyStock> findByCurrencyJPQL(@Param("currency") String currency);

}
