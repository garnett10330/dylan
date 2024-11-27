package com.momo.dylantest.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasePageReq {
    @Schema(description = "當前頁碼", example = "1", defaultValue = "1", required = true)
    private Integer pageNum = 1; // 默認值為 1

    @Schema(description = "每頁數量", example = "10", defaultValue = "10", required = true)
    private Integer pageSize = 10; // 默認值為 10

    @Schema(hidden = true) // 不顯示在 Swagger 文檔中
    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }
}
