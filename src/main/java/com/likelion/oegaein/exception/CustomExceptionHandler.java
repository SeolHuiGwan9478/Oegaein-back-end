package com.likelion.oegaein.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public CustomErrorResponse handleException(CustomException e, HttpServletRequest request) {
        log.error("errorCode: {}, url: {}, message: {}", e.getCustomErrorCode(), request.getRequestURI(), e.getDetailMessage());
        return CustomErrorResponse.builder()
                .status(e.getCustomErrorCode())
                .statusMessage(e.getDetailMessage())
                .build();
    }
}
