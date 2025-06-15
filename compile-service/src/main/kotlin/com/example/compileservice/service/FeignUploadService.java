package com.example.compileservice.service;

import com.example.common.result.Result;
import com.example.common.result.UploadResult;
import com.example.compileservice.feign.FeignUploadClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class FeignUploadService {
    private final FeignUploadClient feignUploadClient;

    @Autowired
    public FeignUploadService(FeignUploadClient feignUploadClient) {
        this.feignUploadClient = feignUploadClient;
    }

    public UploadResult uploadFile(MultipartFile file,String token) {
        ResponseEntity<Result<UploadResult>> result = feignUploadClient.callFileUpload(file, token);
        return result.getBody().getData();
    }

    public UploadResult uploadFile(File file, String token) {
        ResponseEntity<Result<UploadResult>> result = feignUploadClient.callFileUpload(file, token);
        return result.getBody().getData();
    }
}
