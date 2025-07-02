package com.example.uploadservice.service;

import com.aliyuncs.exceptions.ClientException;
import com.example.common.entity.FileInfo;
import com.example.common.entity.User;
import com.example.common.exception.BusinessException;
import com.example.common.exception.ErrorCode;
import com.example.common.exception.HashCalculationException;
import com.example.common.result.UploadResult;
import com.example.demo.common.AliyunOSSOperator;
import com.example.uploadservice.infrastructure.FileInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Override
    public UploadResult saveFile(MultipartFile file, User user) {
        // 检查文件是否为空
        if (file.isEmpty()) {
            throw new BusinessException(ErrorCode.FILE_NOT_FOUND);
        }

        FileInfo fileInfo = new FileInfo();
        // 获取fileId
        String fileId = UUID.randomUUID().toString();
        // 获取fileType
        FileInfo.FileType fileType = getFileType(file);
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
        String encoding = "utf8"; // 默认编码为UTF-8
        switch (encoding) {
            case "utf8":
                fileInfo.setEncoding(FileInfo.Encoding.UTF_8);
                break;
            case "gbk":
                fileInfo.setEncoding(FileInfo.Encoding.GBK);
                break;
            case "ascii":
                fileInfo.setEncoding(FileInfo.Encoding.ASCII);
                break;
        }
        // 获取createAt
        fileInfo.setCreatedAt(LocalDateTime.now());
        // 获取updateAt
        fileInfo.setUpdatedAt(LocalDateTime.now());
        // 获取所有者
        String userName = user.getName();
        fileInfo.setOwner(userName);

        fileInfo.setFileId(fileId);
        fileInfo.setFileType(fileType);
        fileInfo.setFileSize(fileSize);
        fileInfo.setFileName(fileName);
        fileInfo.setHashSha256(hashSha256);

        // 将文件保存到OSS
        String uploadUrl = null;
        byte[] content;
        try {
            content = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            uploadUrl = AliyunOSSOperator.upload(content, fileName);
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
        fileInfo.setFileUrl(uploadUrl);
        fileInfoMapper.addFile(fileInfo);
        return new UploadResult(uploadUrl, fileId);
    }

    public UploadResult saveFile(File file, User user) {
        if (file == null || !file.exists() || file.length() == 0) {
            throw new BusinessException(ErrorCode.FILE_NOT_FOUND);
        }

        FileInfo fileInfo = new FileInfo();
        String fileId = UUID.randomUUID().toString();
        String fileName = file.getName();
        long fileSize = file.length();
        String hashSha256;
        try {
            hashSha256 = calculateSHA256(java.nio.file.Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String encoding = "utf8";
        switch (encoding) {
            case "utf8":
                fileInfo.setEncoding(FileInfo.Encoding.UTF_8);
                break;
            case "gbk":
                fileInfo.setEncoding(FileInfo.Encoding.GBK);
                break;
            case "ascii":
                fileInfo.setEncoding(FileInfo.Encoding.ASCII);
                break;
        }
        fileInfo.setCreatedAt(LocalDateTime.now());
        fileInfo.setUpdatedAt(LocalDateTime.now());
        String userName = user.getName();
        fileInfo.setOwner(userName);

        fileInfo.setFileId(fileId);
        fileInfo.setFileType(determineFileType(StringUtils.getFilenameExtension(fileName)));
        fileInfo.setFileSize(fileSize);
        fileInfo.setFileName(fileName);
        fileInfo.setHashSha256(hashSha256);

        String uploadUrl;
        byte[] content;
        try {
            content = java.nio.file.Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            uploadUrl = AliyunOSSOperator.upload(content, fileName);
        } catch (com.aliyuncs.exceptions.ClientException e) {
            throw new RuntimeException(e);
        }
        fileInfo.setFileUrl(uploadUrl);
        fileInfoMapper.addFile(fileInfo);
        return new UploadResult(uploadUrl, fileId);
    }

    private FileInfo.FileType getFileType(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(originalFilename);
        return determineFileType(fileExtension);
    }

    private FileInfo.FileType determineFileType(String extension) {
        if (extension == null) throw new BusinessException(ErrorCode.FILE_NOT_ALLOWED);

        switch (extension.toLowerCase(Locale.getDefault())) {
            case "c":
                return FileInfo.FileType.C_SOURCE;
            case "h":
                return FileInfo.FileType.HEADER;
            case "a":
                return FileInfo.FileType.STATIC_LIB;
            case "so":
                return FileInfo.FileType.SHARED_LIB;
            case "o":
                return FileInfo.FileType.OBJECT;
            case "mk":
            case "makefile":
                return FileInfo.FileType.MAKEFILE;
            default:
                throw new BusinessException(ErrorCode.FILE_NOT_ALLOWED);
        }
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