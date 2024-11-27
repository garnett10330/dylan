package com.momo.dylantest.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReportWeeklyConditionReq {
    @NotBlank(message = "企業代碼（entpCode）為必填項，不能為空")
    private String entpCode;
    private String goodsCode;
    private Boolean eUnread;
}
