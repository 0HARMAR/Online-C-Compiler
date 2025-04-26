package com.example.demo.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.nio.file.Path;
import java.nio.file.Paths;

@ConfigurationProperties(prefix = "file")
@Validated
@Data
@Configuration
public class FileStorageConfig {
    private static final ThreadLocal<String> baseUploadPath =
            ThreadLocal.withInitial(() -> "./data/upload/");
    private static final ThreadLocal<String> baseOutputPath =
            ThreadLocal.withInitial(() -> "./data/output/");

    public static String getUploadPath(String userName) {
        Path basePath = Paths.get(baseUploadPath.get());
        return basePath.getParent()
                .resolve(userName)
                .resolve(basePath.getFileName())
                .toString();
    }

    public static String getOutputPath(String userName) {
        Path basePath = Paths.get(baseOutputPath.get());
        return basePath.getParent()
                .resolve(userName)
                .resolve(basePath.getFileName())
                .toString();
    }

    public static String getUploadsPath(String userName) {
        Path basePath = Paths.get(baseUploadPath.get());
        return basePath.getParent()
                .resolve(userName)
                .resolve(basePath.getFileName())
                .toString();
    }

    public static String getOutputsPath(String userName) {
        Path basePath = Paths.get(baseOutputPath.get());
        return basePath.getParent()
                .resolve(userName)
                .resolve(basePath.getFileName())
                .toString();
    }
}