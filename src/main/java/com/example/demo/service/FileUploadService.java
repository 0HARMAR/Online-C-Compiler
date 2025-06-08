package com.example.demo.service;

import com.example.demo.common.UploadResult;
import io.jsonwebtoken.Claims;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileUploadService {
    UploadResult saveFile(MultipartFile file, String token);

    UploadResult saveFile(File file, String token);
}
