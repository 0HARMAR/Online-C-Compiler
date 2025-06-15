package com.example.compileservice.feign;

import com.example.common.result.Result;
import com.example.common.result.UploadResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@FeignClient(name="compile-service",url="https://localhost:9090")
public interface FeignUploadClient {
    @PostMapping("/upload")
    ResponseEntity<Result<UploadResult>> callFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestHeader("token") String token
    );

    @PostMapping("/upload")
    ResponseEntity<Result<UploadResult>> callFileUpload(
            @RequestParam("file") File file,
            @RequestHeader("token") String token
    );
}
