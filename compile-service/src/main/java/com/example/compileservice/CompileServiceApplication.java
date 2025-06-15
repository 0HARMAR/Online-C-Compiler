package com.example.compileservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.example.compileservice.feign"})
@MapperScan({"com.example.uploadservice.infrastructure", "com.example.compileservice.infrastructure","com.example.uploadservice.service"})
public class CompileServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CompileServiceApplication.class, args);
    }
}
