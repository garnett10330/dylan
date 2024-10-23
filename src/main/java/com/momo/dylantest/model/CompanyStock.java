package com.momo.dylantest.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "company_stock")
public class CompanyStock {
    /**  `id` int NOT NULL AUTO_INCREMENT COMMENT 'mysql流水號',
     `symbol` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '股票代號',
     `name` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '股票名稱',
     `stockExchange` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '交易所名稱',
     `exchangeShortName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交易所名稱縮寫',
     `currency` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '幣值',
     `gmt_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
     `gmt_modified`
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "symbol")
    private String symbol;
    @Column(name = "name")
    private String name;
    @Column(name = "stock_exchange")
    private String stockExchange;
    @Column(name = "exchange_short_name")
    private String exchangeShortName;
    @Column(name = "currency")
    private String currency;
    @Column(name = "gmt_created")
    private LocalDateTime gmtCreated;
    @Column(name = "gmt_modified")
    private LocalDateTime gmtModified;
}
