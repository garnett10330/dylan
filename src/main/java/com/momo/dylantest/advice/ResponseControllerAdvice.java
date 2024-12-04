package com.momo.dylantest.advice;

import com.momo.dylantest.response.Response;
import com.momo.dylantest.response.swagger.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice(basePackages = "com.momo.dylantest.controller")
@Slf4j
public class ResponseControllerAdvice {
    @ApiResponse(responseCode = "500", description = "ERROR",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ExceptionHandler
    @ResponseBody
    Response<String> handleControllerException(HttpServletRequest request, Throwable ex) {
        log.error(request.getQueryString(), ex);
        if (ex instanceof IllegalArgumentException) {
            return Response.fail(ex.getMessage());
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            return Response.fail("參數格式錯誤");
        } else if (ex instanceof ServletRequestBindingException) {
            return Response.fail("缺少必要參數");
        } else if (ex instanceof DuplicateKeyException) {
            return Response.fail("已存在相同數據");
        } else if (ex instanceof ConstraintViolationException) {
            return Response.fail(ex.getMessage());
        }
        HttpStatus status = getStatus(request);
        return Response.fail(status.value() + ": " + status.getReasonPhrase());

    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
