package com.example.uploadservice.service;

import com.example.common.result.UploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileUploadService {
    UploadResult saveFile(MultipartFile file, String token);

    UploadResult saveFile(File file, String token);
}
