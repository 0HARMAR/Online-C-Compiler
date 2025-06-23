package com.example.uploadservice.controller;


import com.example.common.entity.User;
import com.example.common.result.Result;
import com.example.common.result.UploadResult;
import com.example.uploadservice.service.FileUploadService;
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
    public ResponseEntity<Result<UploadResult>> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestBody User user
    ) {
        UploadResult uploadResult = fileUploadService.saveFile(file, user);;
        return ResponseEntity.ok(Result.success(uploadResult));
    }

}