package com.example.common.result;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CompileResult {
    public Integer exitCode;
    public  String outputFileUrl;
    public  String fileId;
}
