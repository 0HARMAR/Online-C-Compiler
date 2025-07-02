package com.example.configservice;

import com.alibaba.nacos.api.model.v2.Result;
import com.example.configservice.config.JwtUtilsConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;

@RestController
public class ConfigController {
    @PostMapping("/jwt")
    public ResponseEntity<Result<SecretKey>> getJwtKey() {
        // This method would typically return a JWT token.
        // For demonstration purposes, we return a static string.
        return ResponseEntity.ok(
                Result.success(JwtUtilsConfig.generatePublicKey())
        );
    }
}
