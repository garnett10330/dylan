package com.momo.dylantest.controller;

import com.momo.dylantest.controller.fmp.FmpController;
import com.momo.dylantest.model.PageVo;
import com.momo.dylantest.model.dto.CompanyStockDto;
import com.momo.dylantest.model.request.BasePageReq;
import com.momo.dylantest.model.request.StockSymbolSearchReq;
import com.momo.dylantest.service.fmp.CompanyStockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class FmpControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CompanyStockService companyStockService;

    @InjectMocks
    private FmpController fmpController;

    public FmpControllerTest() {
        MockitoAnnotations.openMocks(this); // 初始化 Mock
        this.mockMvc = MockMvcBuilders.standaloneSetup(fmpController).build(); // 構建 MockMvc
    }

    @Test
    void testSaveAllMysql() throws Exception {
        doNothing().when(companyStockService).insertBatchForMysql("Test Company");

        String requestBody = """
                {
                    "companyName": "Test Company"
                }
                """;

        mockMvc.perform(post("/api/stocks/batch/mysql/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1)) // 檢查返回的 code
                .andExpect(jsonPath("$.message").value("OK")); // 檢查返回的 message


        verify(companyStockService, times(1)).insertBatchForMysql("Test Company");
    }

    @Test
    void testGetStocksMysql() throws Exception {
        BasePageReq req = new BasePageReq();
        req.setPageNum(1);
        req.setPageSize(10);

        PageVo<CompanyStockDto> mockPageVo = new PageVo<>();
        when(companyStockService.getAllStocksWithPageMysql(any(BasePageReq.class))).thenReturn(mockPageVo);

        mockMvc.perform(get("/api/stocks/mysql/v1")
                        .param("pageNum", "1")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1)) // 檢查返回的 code
                .andExpect(jsonPath("$.message").value("OK")); // 檢查返回的 message


        verify(companyStockService, times(1)).getAllStocksWithPageMysql(any(BasePageReq.class));
    }

    @Test
    void testSaveAllPostgres() throws Exception {
        doNothing().when(companyStockService).insertBatchForPostgres("Test Company");

        String requestBody = """
                {
                    "companyName": "Test Company"
                }
                """;

        mockMvc.perform(post("/api/stocks/batch/postgres/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1)) // 檢查返回的 code
                .andExpect(jsonPath("$.message").value("OK")); // 檢查返回的 message


        verify(companyStockService, times(1)).insertBatchForPostgres("Test Company");
    }

    @Test
    void testGetStocksPostgres() throws Exception {
        BasePageReq req = new BasePageReq();
        req.setPageNum(1);
        req.setPageSize(10);

        PageVo<CompanyStockDto> mockPageVo = new PageVo<>();
        when(companyStockService.getAllStocksWithPagePostgres(any(BasePageReq.class))).thenReturn(mockPageVo);

        mockMvc.perform(get("/api/stocks/postgres/v1")
                        .param("pageNum", "1")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1)) // 檢查返回的 code
                .andExpect(jsonPath("$.message").value("OK")); // 檢查返回的 message


        verify(companyStockService, times(1)).getAllStocksWithPagePostgres(any(BasePageReq.class));
    }

    @Test
    void testSearchStocks() throws Exception {
        StockSymbolSearchReq req = new StockSymbolSearchReq();
        req.setSymbol("AAPL");
        req.setPageNum(1);
        req.setPageSize(10);

        PageVo<CompanyStockDto> mockPageVo = new PageVo<>();
        when(companyStockService.findStocksBySymbolWithPage(any(StockSymbolSearchReq.class))).thenReturn(mockPageVo);

        String requestBody = """
                {
                    "symbol": "AAPL",
                    "pageNum": 1,
                    "pageSize": 10
                }
                """;

        mockMvc.perform(post("/api/stocks/search/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1)) // 檢查返回的 code
                .andExpect(jsonPath("$.message").value("OK")); // 檢查返回的 message


        verify(companyStockService, times(1)).findStocksBySymbolWithPage(any(StockSymbolSearchReq.class));
    }
}
