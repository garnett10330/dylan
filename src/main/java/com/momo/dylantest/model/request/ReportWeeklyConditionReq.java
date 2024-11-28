package com.momo.dylantest.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReportWeeklyConditionReq {
    @NotBlank(message = "企業代碼（entpCode）為必填項，不能為空")
    @Schema(description = "企業代碼（entpCode）")
    private String entpCode;
    @Schema(description = "品編")
    private String goodsCode;
    @Schema(description = "廠商未讀旗標_0已讀_1未讀")
    private Boolean eUnread;
}
