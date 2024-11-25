package com.momo.dylantest.repository.postgres.custom.customCompanyStock;

import com.momo.dylantest.model.postgres.CompanyStockPostgresPo;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomCompanyStockPostgresRepositoryImpl implements CustomCompanyStockPostgresRepository {
    @PersistenceContext(unitName = "postgresPU")
    private EntityManager entityManager;
    @Resource
    // 注入 MySQL 的 JdbcTemplate
    @Qualifier("postgresJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    @Override
    @Transactional(rollbackFor = Exception.class ,value= "postgresTransactionManager")
    public void insertBatch(List<CompanyStockPostgresPo> list) {
        for (int i = 0; i < list.size(); i++) {
            entityManager.persist(list.get(i));
            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
            // 确保最后一批数据也被刷新
            entityManager.flush();
            entityManager.clear();
        }
    }

//    public <T> void batchUpdate(List<T> list) {
//        if (!ObjectUtils.isEmpty(list)) {
//            for (int i = 0; i < list.size(); i++) {
//                entityManager.merge(list.get(i));
//                if (i % 50 == 0) {
//                    entityManager.flush();
//                    entityManager.clear();
//                }
//            }
//            entityManager.flush();
//            entityManager.clear();
//        }
//    }
    @Override
    @Transactional(rollbackFor = Exception.class ,value= "postgresTransactionManager")
    public void insertBatchFromJdbc(List<CompanyStockPostgresPo> stocks) throws SQLException {
        int batchSize = 1000; // 可以根據實際情況調整
        for (int i = 0; i < stocks.size(); i += batchSize) {
            List<CompanyStockPostgresPo> batch = stocks.subList(i, Math.min(i + batchSize, stocks.size()));
            batchInsertWithRewriteBatchedStatements(batch);
        }
    }
    private void batchInsertWithRewriteBatchedStatements(List<CompanyStockPostgresPo> stocks) throws SQLException {
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
                CompanyStockPostgresPo stock = stocks.get(i);
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
