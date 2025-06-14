package com.example.uploadservice.controller;


import com.example.common.result.Result;
import com.example.common.result.UploadResult;
import com.example.uploadservice.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public ResponseEntity<Result<UploadResult>> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestHeader("token") String token
    ) {
        UploadResult uploadResult = fileUploadService.saveFile(file, token);;
        return ResponseEntity.ok(Result.success(uploadResult));
    }

}