package com.example.common.result;

import com.example.common.exception.*;

import lombok.Data;

@Data
public class Result<T> {  // 使用泛型 T 表示任意数据类型
    private ErrorCode code;     // 状态码
    private String msg;   // 提示信息
    private T data;       // 返回的数据（可以是任何类型）

    // 成功（不带数据）
    public static <T> Result<T> success() {
        return success(null);
    }

    // 成功（带数据）
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ErrorCode.SUCCESS);  // 假设0表示成功
        result.setMsg("success");
        result.setData(data);
        return result;
    }

    // 错误（传递错误码和消息）
    public static <T> Result<T> error(int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(ErrorCode.fromCode(code));
        result.setMsg(msg);
        return result;
    }
}
