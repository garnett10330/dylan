package com.momo.dylantest.repository.custom.customCompanyStock;

import com.momo.dylantest.model.CompanyStock;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public class CustomCompanyStockRepositoryImpl implements CustomCompanyStockRepository{
    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsertJdbc(List<CompanyStock> list) {
        LocalDateTime now = LocalDateTime.now();
        String sql = "INSERT INTO weitest.company_stock (symbol, name, stock_exchange, exchange_short_name, currency) VALUES (?,?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                CompanyStock one = list.get(i);
                ps.setString(1,one.getSymbol());
                ps.setString(2,one.getName());
                ps.setString(3,one.getStockExchange());
                ps.setString(4,one.getExchangeShortName());
                ps.setString(5,one.getCurrency());
            }
            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void highPerformanceBatchInsert(List<CompanyStock> stocks) throws SQLException {
        int batchSize = 1000; // 可以根據實際情況調整

        for (int i = 0; i < stocks.size(); i += batchSize) {
            List<CompanyStock> batch = stocks.subList(i, Math.min(i + batchSize, stocks.size()));
            batchInsertWithRewriteBatchedStatements(batch);
        }
    }
    private void batchInsertWithRewriteBatchedStatements(List<CompanyStock> stocks) throws SQLException {
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
                CompanyStock stock = stocks.get(i);
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
