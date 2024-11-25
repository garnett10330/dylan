package com.momo.dylantest.repository.postgres.custom.customCompanyStock;



import com.momo.dylantest.model.postgres.CompanyStockPostgresPo;

import java.sql.SQLException;
import java.util.List;

public interface CustomCompanyStockPostgresRepository {
    void insertBatch(List<CompanyStockPostgresPo> list);
    void insertBatchFromJdbc(List<CompanyStockPostgresPo> list)  throws SQLException;
}
