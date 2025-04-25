package com.example.demo.controller;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.FileUploadServiceImpl;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class FileUploadController {
    
    @Autowired
    private FileUploadServiceImpl fileUploadServiceImpl;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("encoding") String encoding,
                                                   @RequestAttribute("jwtClaims") Claims claims) {
        fileUploadServiceImpl.saveFile(file,claims,encoding);
        return ResponseEntity.ok("文件上传成功: " + file.getOriginalFilename());
    }

    @PostMapping("/uploads")
    public ResponseEntity<String> handleProjectUploads(
            @RequestParam("file") MultipartFile file,
            @RequestAttribute("jwtClaims") Claims claims
    )
    {
        fileUploadServiceImpl.saveFile(file,claims,"");
        return ResponseEntity.ok("项目上传成功" + file.getOriginalFilename());
    }
}
