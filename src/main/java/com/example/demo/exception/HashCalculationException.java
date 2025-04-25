package com.example.demo.exception;

public class HashCalculationException extends BusinessException {
    public HashCalculationException() {
        super(ErrorCode.HASH_VALUE_CALCULATION_FAILED);
    }
}
