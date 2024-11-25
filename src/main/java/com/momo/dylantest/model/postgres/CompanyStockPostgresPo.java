package com.momo.dylantest.model.postgres;

import com.momo.dylantest.model.dto.api.CompanyStockApiDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CompanyStockPostgresPo {
    /**
     * mysql流水號
     */
    private Integer id;

    /**
     * 股票代號
     */
    private String symbol;

    /**
     * 股票名稱
     */
    private String name;

    /**
     * 交易所名稱
     */
    private String stockExchange;

    /**
     * 交易所名稱縮寫
     */
    private String exchangeShortName;

    /**
     * 幣值
     */
    private String currency;

    /**
     * 創建時間
     */
    private LocalDateTime gmtCreated;

    /**
     * 修改時間
     */
    private LocalDateTime gmtModified;

    // 新增建構子，接受 CompanyStockApiDto 作為參數
    public CompanyStockPostgresPo(CompanyStockApiDto dto) {
        this.symbol = dto.getSymbol();
        this.name = dto.getName();
        this.stockExchange = dto.getStockExchange();
        this.exchangeShortName = dto.getExchangeShortName();
        this.currency = dto.getCurrency();
        this.gmtCreated = dto.getGmtCreated();
        this.gmtModified = dto.getGmtModified();
    }
}
