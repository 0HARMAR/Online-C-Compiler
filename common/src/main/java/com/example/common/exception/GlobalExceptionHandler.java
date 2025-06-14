package com.example.common.exception;

import com.example.common.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result> handleBusinessException(BusinessException be) {
        return new ResponseEntity<Result>(
                Result.error(be.getErrorCode().getCode(),be.getErrorCode().getMessage()),
                HttpStatus.valueOf(200)
        );
    }
}
