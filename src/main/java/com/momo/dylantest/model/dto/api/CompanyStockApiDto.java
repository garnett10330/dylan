package com.momo.dylantest.model.dto.api;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompanyStockApiDto {
    private Integer id;
    private String symbol;
    private String name;
    private String stockExchange;
    private String exchangeShortName;
    private String currency;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModified;
}
