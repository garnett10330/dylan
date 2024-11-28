package com.momo.dylantest.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "公司股票 DTO，包含所有股票相關數據")
public class CompanyStockDto {
    @Schema(description = "流水號", example = "1")
    private Integer id;

    @Schema(description = "股票代號", example = "AAPL")
    private String symbol;

    @Schema(description = "股票名稱", example = "Apple Inc.")
    private String name;

    @Schema(description = "交易所名稱", example = "NASDAQ")
    private String stockExchange;

    @Schema(description = "交易所名稱縮寫", example = "NAS")
    private String exchangeShortName;

    @Schema(description = "幣值", example = "USD")
    private String currency;

    @Schema(description = "創建時間", example = "2023-11-21T15:30:45.123")
    private LocalDateTime gmtCreated;

    @Schema(description = "修改時間", example = "2023-11-21T16:45:30.456")
    private LocalDateTime gmtModified;
}
