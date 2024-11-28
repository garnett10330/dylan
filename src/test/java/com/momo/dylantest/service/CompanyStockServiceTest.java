package com.momo.dylantest.service;

import com.momo.dylantest.api.FmpApi;
import com.momo.dylantest.mapper.mysql.MysqlDBMapper;
import com.momo.dylantest.mapper.postgres.PostgresDBMapper;
import com.momo.dylantest.model.PageVo;
import com.momo.dylantest.model.dto.CompanyStockDto;
import com.momo.dylantest.model.dto.api.CompanyStockApiDto;
import com.momo.dylantest.model.request.BasePageReq;
import com.momo.dylantest.model.request.StockSymbolSearchReq;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class CompanyStockServiceTest {

    @InjectMocks
    private CompanyStockService companyStockService;

    @Mock
    private FmpApi fmpApi;

    @Mock
    private MysqlDBMapper mysqlDBMapper;

    @Mock
    private PostgresDBMapper postgresDBMapper;

    @Test
    void testInsertBatchForMysql() throws Exception {
        // Arrange
        String companyName = "TestCompany";
        List<CompanyStockApiDto> apiDtos = List.of(
                new CompanyStockApiDto(1, "T1", "TestName1", "NASDAQ", "NAS", "USD", LocalDateTime.now(), LocalDateTime.now()),
                new CompanyStockApiDto(2, "T2", "TestName2", "NYSE", "NYS", "USD", LocalDateTime.now(), LocalDateTime.now())
        );
        when(fmpApi.searchCompany(companyName)).thenReturn(apiDtos);
        when(mysqlDBMapper.findCompanyStockCount()).thenReturn(10L);

        // Act
        companyStockService.insertBatchForMysql(companyName);

        // Assert
        verify(fmpApi, times(1)).searchCompany(companyName);
        verify(mysqlDBMapper, times(1)).insertBatchCompanyStock(anyList());
        verify(mysqlDBMapper, times(1)).findCompanyStockCount();
    }
    @Test
    void testGetAllStocksWithPageMysql() {
        // Arrange
        BasePageReq req = new BasePageReq(1, 10); // pageNum=1, pageSize=10
        List<CompanyStockDto> mockStocks = List.of(
                new CompanyStockDto(1, "T1", "TestName1", "NASDAQ", "NAS", "USD", LocalDateTime.now(), LocalDateTime.now()),
                new CompanyStockDto(2, "T2", "TestName2", "NYSE", "NYS", "USD", LocalDateTime.now(), LocalDateTime.now())
        );
        when(mysqlDBMapper.findCompanyStockPage(req.getOffset(), req.getPageSize())).thenReturn(mockStocks);
        when(mysqlDBMapper.findCompanyStockCount()).thenReturn(100L);

        // Act
        PageVo<CompanyStockDto> result = companyStockService.getAllStocksWithPageMysql(req);

        // Assert
        assertEquals(2, result.getResult().size());
        assertEquals(100L, result.getTotal());
        verify(mysqlDBMapper, times(1)).findCompanyStockPage(req.getOffset(), req.getPageSize());
        verify(mysqlDBMapper, times(1)).findCompanyStockCount();
    }

    @Test
    void testInsertBatchForPostgres() throws Exception {
        // Arrange
        String companyName = "TestCompany";
        List<CompanyStockApiDto> apiDtos = List.of(
                new CompanyStockApiDto(1, "T1", "TestName1", "NASDAQ", "NAS", "USD", LocalDateTime.now(), LocalDateTime.now()),
                new CompanyStockApiDto(2, "T2", "TestName2", "NYSE", "NYS", "USD", LocalDateTime.now(), LocalDateTime.now())
        );
        when(fmpApi.searchCompany(companyName)).thenReturn(apiDtos);
        when(postgresDBMapper.findCompanyStockCount()).thenReturn(5L);

        // Act
        companyStockService.insertBatchForPostgres(companyName);

        // Assert
        verify(fmpApi, times(1)).searchCompany(companyName);
        verify(postgresDBMapper, times(1)).insertBatchCompanyStock(anyList());
        verify(postgresDBMapper, times(1)).findCompanyStockCount();
    }

    @Test
    void testGetAllStocksWithPagePostgres() {
        // Arrange
        BasePageReq req = new BasePageReq(1, 10); // pageNum=1, pageSize=10
        List<CompanyStockDto> mockStocks = List.of(
                new CompanyStockDto(1, "T1", "TestName1", "NASDAQ", "NAS", "USD", LocalDateTime.now(), LocalDateTime.now()),
                new CompanyStockDto(2, "T2", "TestName2", "NYSE", "NYS", "USD", LocalDateTime.now(), LocalDateTime.now())
        );
        when(postgresDBMapper.findCompanyStockPage(req.getOffset(), req.getPageSize())).thenReturn(mockStocks);
        when(postgresDBMapper.findCompanyStockCount()).thenReturn(100L);

        // Act
        PageVo<CompanyStockDto> result = companyStockService.getAllStocksWithPagePostgres(req);

        // Assert
        assertEquals(2, result.getResult().size());
        assertEquals(100L, result.getTotal());
        verify(postgresDBMapper, times(1)).findCompanyStockPage(req.getOffset(), req.getPageSize());
        verify(postgresDBMapper, times(1)).findCompanyStockCount();
    }

    @Test
    void testFindStocksBySymbolWithPage() {
        // Arrange
        StockSymbolSearchReq req = new StockSymbolSearchReq();
        req.setSymbol("T1");
        req.setPageNum(1);
        req.setPageSize(10);
        List<CompanyStockDto> mockStocks = List.of(
                new CompanyStockDto(1, "T1", "TestName1", "NASDAQ", "NAS", "USD", LocalDateTime.now(), LocalDateTime.now())
                       );
        when(mysqlDBMapper.findBySymbolWithPage("T1", req.getOffset(), req.getPageSize())).thenReturn(mockStocks);
        when(mysqlDBMapper.countBySymbol("T1")).thenReturn(1L);

        // Act
        PageVo<CompanyStockDto> result = companyStockService.findStocksBySymbolWithPage(req);

        // Assert
        assertEquals(1, result.getResult().size());
        assertEquals(1L, result.getTotal());
        verify(mysqlDBMapper, times(1)).findBySymbolWithPage("T1", req.getOffset(), req.getPageSize());
        verify(mysqlDBMapper, times(1)).countBySymbol("T1");
    }
}
