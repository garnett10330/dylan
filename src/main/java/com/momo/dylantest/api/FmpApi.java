package com.momo.dylantest.api;

import com.momo.dylantest.model.CompanyStock;
import jakarta.annotation.Resource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class FmpApi {
    @Resource
    private RestClient restClient;
    static String API_KEY = "rtBlUdMrEgWdwWDahqfYewEPcIxa5jTr";
    static String FINANCIAL_MODEL_URL = "https://financialmodelingprep.com/api/v3/search?query=";


    public List<CompanyStock> searchCompany(String company){
        return  restClient.get()
                .uri(FINANCIAL_MODEL_URL+company+"&apikey="+API_KEY)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
}
