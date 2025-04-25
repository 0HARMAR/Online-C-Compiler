package com.example.demo.service;

import com.example.demo.model.entity.CompileConfig;
import io.jsonwebtoken.Claims;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

public interface CompileService {
    InputStreamResource preCompile(CompileConfig option, Claims claims);
}
