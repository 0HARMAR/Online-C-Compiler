package com.example.demo.service;

import io.jsonwebtoken.Claims;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    void saveFile(MultipartFile file, Claims claims, String encoding);
}
