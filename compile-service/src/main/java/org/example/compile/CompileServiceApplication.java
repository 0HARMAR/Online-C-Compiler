package org.example.compile;

import com.example.uploadservice.UploadServiceApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.example.uploadservice.infrastructure", "org.example.compile.infrastructure"})

public class CompileServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CompileServiceApplication.class, args);
    }

}
