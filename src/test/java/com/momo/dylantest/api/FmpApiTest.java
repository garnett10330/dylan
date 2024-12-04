package com.momo.dylantest.api;

import com.momo.dylantest.model.dto.api.CompanyStockApiDto;
import com.momo.dylantest.properties.FmpApiConfigProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class FmpApiTest {

    @InjectMocks
    private FmpApi fmpApi;

    @Mock
    private RestClient restClient;

    @Mock
    private FmpApiConfigProperties fmpApiConfig;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        // 模擬配置
        when(fmpApiConfig.getBaseUrl()).thenReturn("https://financialmodelingprep.com/api/v3/search");
        when(fmpApiConfig.getApiKey()).thenReturn("test-api-key");
    }

    @Test
    void testSearchCompany() {
        // 模擬返回值
        String company = "Microsoft";
        List<CompanyStockApiDto> mockResponse = List.of(
                new CompanyStockApiDto(
                        1,
                        "MSFT",
                        "Microsoft Corporation",
                        "NASDAQ",
                        "NAS",
                        "USD",
                        LocalDateTime.of(2023, 11, 20, 10, 0),
                        LocalDateTime.of(2023, 11, 21, 12, 0)
                ),
                new CompanyStockApiDto(
                        2,
                        "MSFTLAB",
                        "Microsoft Labs",
                        "NASDAQ",
                        "NAS",
                        "USD",
                        LocalDateTime.of(2023, 11, 22, 8, 0),
                        LocalDateTime.of(2023, 11, 23, 14, 0)
                )
        );

        var mockRequest = mock(RestClient.RequestHeadersUriSpec.class);
        var mockRetrieve = mock(RestClient.ResponseSpec.class);

        when(restClient.get()).thenReturn(mockRequest);
        when(mockRequest.uri(anyString())).thenReturn(mockRequest);
        when(mockRequest.retrieve()).thenReturn(mockRetrieve);
        when(mockRetrieve.body(any(ParameterizedTypeReference.class))).thenReturn(mockResponse);

        // 調用方法
        List<CompanyStockApiDto> result = fmpApi.searchCompany(company);

        // 驗證結果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("MSFT", result.get(0).getSymbol());
        assertEquals("Microsoft Corporation", result.get(0).getName());

        // 驗證 RestClient 被正確調用
        verify(mockRequest).uri(contains(company));
    }
}
