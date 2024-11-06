package com.momo.dylantest.api;

import com.momo.dylantest.model.CompanyStock;
import jakarta.annotation.Resource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class AwsPushApi {
    @Resource
    private RestClient restClient;
    static String apiKey = "rtBlUdMrEgWdwWDahqfYewEPcIxa5jTr";


    public List<CompanyStock> searchCompany(String company){
        return  restClient.get()
                .uri("https://financialmodelingprep.com/api/v3/search?query="+company+"&apikey="+apiKey)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
}
