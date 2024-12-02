package com.momo.dylantest.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FmpSaveAllReq {
    @NotBlank(message = "要保存的公司名稱為必填項，不能為空")
    @Schema(description = "要保存的公司名稱", example = "AAPL")
    private String companyName;
}
