package com.example.common.exception;

public enum ErrorCode {

    SUCCESS(0, "成功"),
    FILE_NOT_FOUND(1, "文件不存在"),
    FILE_NOT_ALLOWED(2,"文件不合法，请上传正确的源文件或项目文件"),
    HASH_VALUE_CALCULATION_FAILED(3,"哈希值计算失败"),
    UNKNOW_CODE(-1,"未知错误码");

    private final int code;        // 错误码
    private final String message;  // 错误描述

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 根据错误码获取对应的枚举
     * @param code 错误码
     * @return 对应的枚举，默认返回 SYSTEM_ERROR
     */
    public static ErrorCode fromCode(int code) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.getCode() == code) {
                return errorCode;
            }
        }
        return UNKNOW_CODE;
    }
}