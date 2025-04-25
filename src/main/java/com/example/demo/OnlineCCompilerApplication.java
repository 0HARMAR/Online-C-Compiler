package com.example.demo;

import com.example.demo.configuration.FileStorageConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan("com.example.demo.dao.mapper")
@EnableConfigurationProperties(FileStorageConfig.class)
public class OnlineCCompilerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineCCompilerApplication.class, args);
	}

}
