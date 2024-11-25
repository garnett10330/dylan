package com.momo.dylantest.model.mysql;

import com.momo.dylantest.model.dto.api.CompanyStockApiDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/**
 * Company Stock MySQL Pojo
 * */
@Data
@NoArgsConstructor
public class CompanyStockMysqlPo {
    /**
     * Company Stock MySQL Pojo
     *
     * 對應資料表欄位與註解如下：
     * `id` int NOT NULL AUTO_INCREMENT COMMENT 'mysql流水號',
     * `symbol` varchar(10) COMMENT '股票代號',
     * `name` varchar(225) COMMENT '股票名稱',
     * `stock_exchange` varchar(225) COMMENT '交易所名稱',
     * `exchange_short_name` varchar(100) NOT NULL COMMENT '交易所名稱縮寫',
     * `currency` varchar(30) NOT NULL COMMENT '幣值',
     * `gmt_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
     * `gmt_modified` timestamp(3) DEFAULT NULL
     */
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
    public CompanyStockMysqlPo(CompanyStockApiDto dto) {
        this.symbol = dto.getSymbol();
        this.name = dto.getName();
        this.stockExchange = dto.getStockExchange();
        this.exchangeShortName = dto.getExchangeShortName();
        this.currency = dto.getCurrency();
        this.gmtCreated = dto.getGmtCreated();
        this.gmtModified = dto.getGmtModified();
    }

}
