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
    private CompileServiceImpl compileServiceImpl;

    @PostMapping("/compile")
    public ResponseEntity<Result<String>> handleCompile(
            @RequestBody CompileConfig option,
            @RequestAttribute("jwtClaims") Claims claims) {
        InputStreamResource outputFile = compileServiceImpl.preCompile(option,claims);
        return new ResponseEntity<>(Result.success("编译成功"),HttpStatus.OK);
    }

    @PostMapping("/compiles")
    public ResponseEntity<Result<String>> handleCompiles(
            @RequestBody CompileConfig option,
            @RequestAttribute("jwtClaims") Claims claims) {
        return new ResponseEntity<>(Result.success("编译成功"),HttpStatus.OK);
    }

    @PostMapping("/download")
    public ResponseEntity<InputStreamResource> handleDownload(
            @RequestAttribute("jwtClaims") Claims claims
    ) throws IOException {
        String userName = JwtUtils.getUsername(claims);
        String outputFilePath = FileStorageConfig.getOutputPath(userName);
        File file = new File(outputFilePath);

        // 构建响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");

        // 检测mime类型
        MediaType contentType = MediaType.parseMediaType(
                Files.probeContentType(file.toPath()) // 自动检测
        );
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM; // 默认类型
        }

        InputStreamResource resource = compileServiceImpl.getOutputFile(userName,outputFilePath);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(contentType)
                .contentLength(file.length())
                .body(resource);
    }
}
