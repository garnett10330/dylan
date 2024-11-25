package com.momo.dylantest.repository.mysql;

import com.momo.dylantest.model.dto.CompanyStockDto;
import com.momo.dylantest.model.mysql.CompanyStockMysqlPo;
import com.momo.dylantest.repository.mysql.custom.customCompanyStock.CustomCompanyStockMysqlRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyStockMysqlRepository extends JpaRepository<CompanyStockMysqlPo, Integer>, CustomCompanyStockMysqlRepository {
    @Query(value = "SELECT id, symbol, name, stock_exchange, exchange_short_name, currency, " +
            "gmt_created, gmt_modified FROM company_stock WHERE 1=1 ", nativeQuery = true)
    Page<CompanyStockDto> findAllDto(Pageable pageable);

}
