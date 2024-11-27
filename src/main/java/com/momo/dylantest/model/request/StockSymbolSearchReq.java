package com.momo.dylantest.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockSymbolSearchReq extends BasePageReq{
    //@Schema(description = "股票代號", example = "AAPL", required = true)
    @NotNull(message = "頁碼不能為空")
    /**
     * "股票代號"
     * */
    private String symbol;
}
