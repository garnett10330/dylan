package com.momo.dylantest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class Response<T> {
    private int statusCode;
    @JsonProperty(value = "message")
    private String message;
    @JsonProperty(value = "data")
    private T data;
    public Response() {
    }

    public Response(String message, int statusCode, T data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }


    public static <T> Response<T> success(T data) {
        return new Response<>("OK", 200, data);
    }

    public static <T> Response<T> success() {
        return new Response<>("OK", 200, null);
    }

    public static <T> Response<T> fail(String message){

        return new Response<>(message,-1,null);
    }
}
