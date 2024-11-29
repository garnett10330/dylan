package com.momo.dylantest.response.swagger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.momo.dylantest.constant.ResponseCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "錯誤 API 響應結構 僅用在swagger")
public class ErrorResponse<T> {
    @Schema(
            description = "返回狀態， -1 (失敗)",
            allowableValues = {"-1"}
    )
    private int code;
    @JsonProperty(value = "message")
    @Schema(description = "錯誤訊息", example = "Internal Server Error")
    private String message;
    @JsonProperty(value = "data")
    @Schema(description = "返回數據")
    private T data;
    public ErrorResponse() {
    }

    public ErrorResponse(String message, int code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }



    public static <T> ErrorResponse<T> fail(String message){
        return new ErrorResponse<>(message,ResponseCodeEnum.FAIL.getValue(),null);
    }
}
