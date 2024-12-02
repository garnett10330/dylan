package com.momo.dylantest.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MessageTestReq {
    @NotBlank(message = "發送的消息內容為必填項，不能為空")
    @Schema(description = "要發送的消息內容", example = "test")
    private String message;
}
