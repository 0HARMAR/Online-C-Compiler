package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.service.FileUploadService;
import com.example.demo.service.FileUploadServiceImpl;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public ResponseEntity<Result<String>> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestHeader("token") String token
    ) {
        String uploadUrl = fileUploadService.saveFile(file, token);;
        return ResponseEntity.ok(Result.success(uploadUrl));
    }

}