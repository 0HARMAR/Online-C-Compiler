package com.example.demo.service;

import io.jsonwebtoken.Claims;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String saveFile(MultipartFile file, String token);
}
