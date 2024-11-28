package com.momo.dylantest.model.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
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
