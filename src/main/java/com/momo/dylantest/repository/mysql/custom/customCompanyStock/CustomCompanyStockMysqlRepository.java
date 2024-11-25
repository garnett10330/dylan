package com.momo.dylantest.repository.mysql.custom.customCompanyStock;

import com.momo.dylantest.model.mysql.CompanyStockMysqlPo;

import java.sql.SQLException;
import java.util.List;

public interface CustomCompanyStockMysqlRepository {
    void insertBatchFromJdbc(List<CompanyStockMysqlPo> list)  throws SQLException;
}
