package com.momo.dylantest.mapper.postgres;

import com.momo.dylantest.model.dto.CompanyStockDto;
import com.momo.dylantest.model.postgres.CompanyStockPostgresPo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface CompanyStockPostgresMapper {

    /**
     * 批量插入公司股票信息
     *
     * @param companyStockList 公司股票列表
     * @return 插入的行數
     */
    @Insert({
            "<script>",
            "INSERT INTO company_stock (symbol, name, stock_exchange, exchange_short_name, currency)",
            "VALUES",
            "<foreach collection='companyStockList' item='item' separator=','>",
            "( #{item.symbol}, #{item.name}, #{item.stockExchange}, #{item.exchangeShortName}, ",
            "#{item.currency})",
            "</foreach>",
            "</script>"
    })
    int insertBatchCompanyStock(@Param("companyStockList") List<CompanyStockPostgresPo> companyStockList);

    /**
     * 查詢所有股票 DTO，支持分頁
     *
     * @param offset 起始位置
     * @param pageSize 每頁記錄數
     * @return 股票列表
     */
    @Select({
            "SELECT id, symbol, name, stock_exchange, exchange_short_name, currency, ",
            "gmt_created, gmt_modified ",
            "FROM company_stock ",
            "LIMIT #{pageSize} OFFSET #{offset}"
    })
    List<CompanyStockDto> findCompanyStockPage(@Param("offset") int offset, @Param("pageSize") int pageSize);

    @Select({"SELECT count(1) FROM company_stock "})
    long findCompanyStockCount();
}
