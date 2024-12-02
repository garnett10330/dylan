package com.momo.dylantest.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RedisSetReq {

    @NotBlank(message = "redis key，不能為空")
    @Schema(description = "redis key")
    private String key;
    @NotBlank(message = "redis data，不能為空")
    @Schema(description = "redis data value")
    private String data;
}
