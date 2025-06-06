package com.example.demo.controller;

import com.example.demo.common.JwtUtils;
import com.example.demo.common.Result;
import com.example.demo.configuration.FileStorageConfig;
import com.example.demo.model.entity.CompileConfig;
import com.example.demo.service.CompileServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.CompileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@RestController
public class CompileController {
    @Autowired
    private CompileService compileService;

    @PostMapping("/compile")
    public ResponseEntity<Result<String>> handleCompile(
            @RequestBody CompileConfig option,
            @RequestHeader("token") String token,
            @RequestParam String fileId) {
        // 返回编译结果OSS下载链接
        String outputUrl = compileService.compile(option,token,fileId);
        return new ResponseEntity<>(Result.success(outputUrl),HttpStatus.OK);
    }

    @PostMapping("/compiles")
    public ResponseEntity<Result<String>> handleCompiles(
            @RequestBody CompileConfig option,
            @RequestAttribute("jwtClaims") Claims claims) {
        return new ResponseEntity<>(Result.success("编译成功"),HttpStatus.OK);
    }

}
