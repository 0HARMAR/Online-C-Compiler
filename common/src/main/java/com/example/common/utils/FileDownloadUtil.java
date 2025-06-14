package com.example.common.utils;

import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileDownloadUtil {
    
    /**
     * Downloads a file from the given URL and saves it to a temporary location
     * @param fileUrl the URL of the file to download
     * @return the downloaded file
     * @throws IOException if an error occurs during download
     */
    public static File downloadFile(String fileUrl) throws IOException {
        // Create temp directory if it doesn't exist
        Path tempDir = Files.createDirectories(Path.of(System.getProperty("java.io.tmpdir"), "online-compiler"));
        
        // Generate unique filename using timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = timestamp + "_" + new File(new URL(fileUrl).getPath()).getName();
        File targetFile = tempDir.resolve(fileName).toFile();
        
        // Download file from URL
        try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
            URL url = new URL(fileUrl);
            FileCopyUtils.copy(url.openStream(), outputStream);
        }
        
        return targetFile;
    }
} 