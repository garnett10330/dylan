package com.momo.dylantest.repository.custom.customCompanyStock;

import com.momo.dylantest.model.CompanyStock;

import java.sql.SQLException;
import java.util.List;

public interface CustomCompanyStockRepository {
    void batchInsertJdbc(List<CompanyStock> list);
    void highPerformanceBatchInsert(List<CompanyStock> list)  throws SQLException;
}
