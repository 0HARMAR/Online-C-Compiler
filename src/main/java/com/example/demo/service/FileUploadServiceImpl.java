package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.demo.common.JwtUtils;
import com.example.demo.configuration.FileStorageConfig;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.HashCalculationException;
import io.jsonwebtoken.Claims;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.mapper.FileInfoMapper;
import com.example.demo.model.entity.FileInfo;
import com.example.demo.model.entity.FileInfo.FileType;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private FileInfoMapper fileInfoMapper;

    public void saveFile(MultipartFile file, Claims claims, String encoding) {
            // 检查文件是否为空
            if (file.isEmpty()) {
                throw new BusinessException(ErrorCode.FILE_NOT_FOUND);
            }
            // 获取文件存储路径
            String userName = JwtUtils.getUsername(claims);
            String uploadPath = FileStorageConfig.getUploadPath(userName);

            FileInfo fileInfo = new FileInfo();
            // 获取fileId
            String fileId = UUID.randomUUID().toString();
            // 获取fileType
            FileType fileType = getFileType(file);
            // 获取filePath
            String filePath = getFilePath(file,uploadPath);
            // 获取fileName
            String fileName = file.getOriginalFilename();
            // 获取fileSize
            long fileSize = file.getSize();
            // 获取hash值
            String hashSha256;
            try {
                hashSha256 = calculateSHA256(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 获取encoding
            switch (encoding){
                case "utf8":
                    fileInfo.setEncoding(FileInfo.Encoding.UTF_8.getEncoding());
                    break;
                case "gbk":
                    fileInfo.setEncoding(FileInfo.Encoding.GBK.getEncoding());
                    break;
                case "ascii":
                    fileInfo.setEncoding(FileInfo.Encoding.ASCII.getEncoding());
                    break;
            }
            // 获取createAt
            fileInfo.setCreatedAt(LocalDateTime.now());
            // 获取updateAt
            fileInfo.setUpdatedAt(LocalDateTime.now());
            // 获取所有者
            fileInfo.setOwner(userName);

            fileInfo.setFileId(fileId);
            fileInfo.setFileType(fileType);
            fileInfo.setFileSize(fileSize);
            fileInfo.setFilePath(filePath);
            fileInfo.setFileName(fileName);
            fileInfo.setHashSha256(hashSha256);
            fileInfoMapper.addFile(fileInfo);

            // 将文件保存到本地
            try {
                file.transferTo(new File(getFilePath(file,uploadPath)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    private FileType getFileType(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(originalFilename);
        FileInfo.FileType fileType = determineFileType(fileExtension);
        return fileType;
    }

    private FileInfo.FileType determineFileType(String extension) {
        if (extension == null) throw new BusinessException(ErrorCode.FILE_NOT_ALLOWED);
        
        return switch (extension.toLowerCase()) {
            case "c" -> FileInfo.FileType.C_SOURCE;
            case "h" -> FileInfo.FileType.HEADER;
            case "a" -> FileInfo.FileType.STATIC_LIB;
            case "so" -> FileInfo.FileType.SHARED_LIB;
            case "o" -> FileInfo.FileType.OBJECT;
            case "mk", "makefile" -> FileInfo.FileType.MAKEFILE;
            default -> throw new BusinessException(ErrorCode.FILE_NOT_ALLOWED);
        };
    }

    private String getFilePath(MultipartFile file,String uploadPath) {
        String fileName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "unknown";
        return uploadPath + '/' + fileName;
    }

    // SHA256计算方法
    private String calculateSHA256(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data);
            return bytesToHex(hashBytes);
        } catch (Exception e) {
            throw new HashCalculationException();
        }
    }
    // 字节转十六进制
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
