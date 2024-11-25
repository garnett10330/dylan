package com.momo.dylantest.repository.mysql.custom.customCompanyStock;

import com.momo.dylantest.model.mysql.CompanyStockMysqlPo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
@Repository
public class CustomCompanyStockMysqlRepositoryImpl implements CustomCompanyStockMysqlRepository {
    @Resource
    // 注入 MySQL 的 JdbcTemplate
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class ,value= "mysqlTransactionManager")
    public void insertBatchFromJdbc(List<CompanyStockMysqlPo> stocks) throws SQLException {
        int batchSize = 1000; // 可以根據實際情況調整
        for (int i = 0; i < stocks.size(); i += batchSize) {
            List<CompanyStockMysqlPo> batch = stocks.subList(i, Math.min(i + batchSize, stocks.size()));
            batchInsertWithRewriteBatchedStatements(batch);
        }
    }
    private void batchInsertWithRewriteBatchedStatements(List<CompanyStockMysqlPo> stocks) throws SQLException {
        StringBuilder sql = new StringBuilder("INSERT INTO company_stock (symbol, name, stock_exchange, exchange_short_name, currency) VALUES ");
        for (int i = 0; i < stocks.size(); i++) {
            sql.append("(?, ?, ?, ?, ?)");
            if (i < stocks.size() - 1) {
                sql.append(", ");
            }
        }
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql.toString());
            for (int i = 0; i < stocks.size(); i++) {
                CompanyStockMysqlPo stock = stocks.get(i);
                ps.setString(i * 5 + 1, stock.getSymbol());
                ps.setString(i * 5 + 2, stock.getName());
                ps.setString(i * 5 + 3, stock.getStockExchange());
                ps.setString(i * 5 + 4, stock.getExchangeShortName());
                ps.setString(i * 5 + 5, stock.getCurrency());
            }
            return ps;
        });
    }


}
