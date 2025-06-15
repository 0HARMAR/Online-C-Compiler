package com.example.compileservice.controller;

import com.example.common.result.Result;
import com.example.common.entity.CompileConfig;
import com.example.common.result.CompileResult;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.compileservice.service.CompileService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class CompileController {
    @Autowired
    private CompileService compileService;

    @PostMapping("/compile")
    public ResponseEntity<Result<CompileResult>> handleCompile(
            @RequestBody CompileConfig option,
            @RequestHeader("token") String token,
            @RequestParam String fileId) {
        // 返回编译结果OSS下载链接
        CompileResult compileResult;
        compileResult = compileService.compile(option,token,fileId);
        return new ResponseEntity<>(Result.success(compileResult),HttpStatus.OK);
    }

    @PostMapping("/compiles")
    public ResponseEntity<Result<String>> handleCompiles(
            @RequestBody CompileConfig option,
            @RequestAttribute("jwtClaims") Claims claims) {
        return new ResponseEntity<>(Result.success("编译成功"),HttpStatus.OK);
    }
}
