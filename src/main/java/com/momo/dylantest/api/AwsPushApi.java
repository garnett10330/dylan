package com.momo.dylantest.api;

import com.momo.dylantest.model.mysql.CompanyStockMysqlPo;
import jakarta.annotation.Resource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class AwsPushApi {
    @Resource
    private RestClient restClient;
    static final String API_KEY = "rtBlUdMrEgWdwWDahqfYewEPcIxa5jTr";


    public List<CompanyStockMysqlPo> searchCompany(String company){
        return  restClient.get()
                .uri("https://financialmodelingprep.com/api/v3/search?query="+company+"&apikey="+API_KEY)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
}
