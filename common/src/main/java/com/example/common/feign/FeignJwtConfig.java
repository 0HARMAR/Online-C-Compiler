package com.example.common.feign;

import com.alibaba.nacos.api.model.v2.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import javax.crypto.SecretKey;

@FeignClient(name = "config-service")
public interface FeignJwtConfig {
    @PostMapping("/jwt")
    ResponseEntity<Result<SecretKey>> callGetJwtKey();
}
