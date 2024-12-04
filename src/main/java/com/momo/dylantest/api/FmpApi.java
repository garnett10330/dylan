package com.momo.dylantest.api;

import com.momo.dylantest.model.dto.api.CompanyStockApiDto;
import com.momo.dylantest.properties.FmpApiConfigProperties;
import jakarta.annotation.Resource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class FmpApi {
    @Resource
    private RestClient restClient;
    @Resource
    private FmpApiConfigProperties fmpApiConfig;


    public List<CompanyStockApiDto> searchCompany(String company){
        String url = String.format("%s?query=%s&apikey=%s",
                fmpApiConfig.getBaseUrl(),
                company,
                fmpApiConfig.getApiKey());

        return  restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
}
