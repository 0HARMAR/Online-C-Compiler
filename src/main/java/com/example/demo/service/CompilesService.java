package com.example.demo.service;

import com.example.demo.model.entity.CompileConfig;
import io.jsonwebtoken.Claims;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface CompilesService {
    InputStreamResource handleCompiles(CompileConfig option, Claims claims);
}
