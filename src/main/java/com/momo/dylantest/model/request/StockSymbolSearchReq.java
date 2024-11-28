package com.momo.dylantest.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockSymbolSearchReq extends BasePageReq{
    @NotNull(message = "頁碼不能為空")
    @Schema(description = "股票代碼")
    private String symbol;
}
