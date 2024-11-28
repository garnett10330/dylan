package com.momo.dylantest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.momo.dylantest.constant.ResponseCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@Schema(description = "通用 API 響應結構")
public class Response<T> {
    @Schema(
            description = "返回狀態，可能的值：1 (成功), -1 (失敗)",
            allowableValues = {"1", "-1"}
    )
    private int code;
    @JsonProperty(value = "message")
    @Schema(description = "返回訊息")
    private String message;
    @JsonProperty(value = "data")
    @Schema(description = "返回數據")
    private T data;
    public Response() {
    }

    public Response(String message, int code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }


    public static <T> Response<T> success(T data) {
        return new Response<>("OK", ResponseCodeEnum.SUCCESS.getValue(), data);
    }

    public static <T> Response<T> success() {
        return new Response<>("OK", ResponseCodeEnum.SUCCESS.getValue(), null);
    }

    public static <T> Response<T> fail(String message){

        return new Response<>(message,ResponseCodeEnum.FAIL.getValue(),null);
    }
}
