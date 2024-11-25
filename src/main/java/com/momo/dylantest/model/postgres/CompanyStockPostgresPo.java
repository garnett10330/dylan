package com.momo.dylantest.model.postgres;

import com.momo.dylantest.model.dto.api.CompanyStockApiDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "company_stock")
public class CompanyStockPostgresPo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_stock_seq")
    @SequenceGenerator(name = "company_stock_seq", sequenceName = "company_stock_sequence", allocationSize = 1)
    private Integer id;

    @Column(name = "symbol", nullable = false, length = 225)
    private String symbol;

    @Column(name = "name", nullable = false, length = 225)
    private String name;

    @Column(name = "stock_exchange", length = 225)
    private String stockExchange;

    @Column(name = "exchange_short_name", length = 100)
    private String exchangeShortName;

    @Column(name = "currency", length = 30)
    private String currency;

    @Column(name = "gmt_created", nullable = false, insertable = false, columnDefinition = "TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)")
    private LocalDateTime gmtCreated;

    @Column(name = "gmt_modified", nullable = false, insertable = false, columnDefinition = "TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)")
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
