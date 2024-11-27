package com.momo.dylantest.mapper.mysql;

import com.momo.dylantest.model.dto.CompanyStockDto;
import com.momo.dylantest.model.mysql.CompanyStockMysqlPo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface CompanyStockMapper {

    /**
     * 批量插入公司股票信息
     *
     * @param companyStockList 公司股票列表
     * @return 插入的行數
     */
    @Insert({
            "<script>",
            "INSERT INTO company_stock (`symbol`, `name`, `stock_exchange`, `exchange_short_name`, `currency`)",
            "VALUES",
            "<foreach collection='companyStockList' item='item' separator=','>",
            "(#{item.symbol}, #{item.name}, #{item.stockExchange}, #{item.exchangeShortName}, #{item.currency})",
            "</foreach>",
            "</script>"
    })
    int insertBatchCompanyStock(@Param("companyStockList") List<CompanyStockMysqlPo> companyStockList);

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
            "WHERE 1=1 ",
            "LIMIT #{offset}, #{pageSize}"
    })
    List<CompanyStockDto> findCompanyStockPage(@Param("offset") int offset, @Param("pageSize") int pageSize);

    /**
     * 查詢數據總數。
     * @return table的總數。
     */
    @Select({"SELECT count(1) FROM company_stock "})
    long findCompanyStockCount();

    /**
     * 根據股票代號（symbol）和分頁條件查詢公司股票數據。
     *
     * @param symbol 股票代號。
     * @param offset 偏移量（分頁起始位置）。
     * @param pageSize 每頁條數。
     * @return 符合條件的公司股票數據列表。
     */
    @Select({
            "<script>",
            "SELECT symbol, name, stock_exchange, exchange_short_name, currency,gmt_created, gmt_modified FROM company_stock",
            "WHERE 1=1",
            "<if test='symbol != null and symbol != \"\"'>",
            "AND symbol LIKE CONCAT('%', #{symbol}, '%')",
            "</if>",
            "LIMIT #{offset}, #{pageSize}",
            "</script>"
    })
    List<CompanyStockDto> findBySymbolWithPage(@Param("symbol") String symbol, @Param("offset") int offset, @Param("pageSize") int pageSize);

    /**
     * 根據股票代號（symbol）查詢數據總數。
     *
     * @param symbol 股票代號。
     * @return 符合條件的總數。
     */
    @Select({
            "<script>",
            "SELECT COUNT(1) FROM company_stock",
            "WHERE 1=1",
            "<if test='symbol != null and symbol != \"\"'>",
            "AND symbol LIKE CONCAT('%', #{symbol}, '%')",
            "</if>",
            "</script>"
    })
    long countBySymbol(@Param("symbol") String symbol);
}
