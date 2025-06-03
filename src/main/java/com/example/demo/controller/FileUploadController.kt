package com.example.demo.controller

import com.example.demo.service.FileUploadServiceImpl
import io.jsonwebtoken.Claims
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@Slf4j
class FileUploadController {
    @Autowired
    private val fileUploadServiceImpl: FileUploadServiceImpl? = null

    @PostMapping("/upload")
    fun handleFileUpload(
        @RequestParam("file") file: MultipartFile,
        @RequestAttribute("jwtClaims") claims: Claims?
    ): ResponseEntity<Result<String>> {
        claims?.let { encoding?.let { it1 -> fileUploadServiceImpl!!.saveFile(file, it, it1) } }
        // TODO 返回上传文件下载地址
        String uploadUrl = ""
        return ResponseEntity.ok(Result.success(uploadUrl))
    }

    @PostMapping("/uploads")
    fun handleProjectUploads(
        @RequestParam("file") file: MultipartFile,
        @RequestAttribute("jwtClaims") claims: Claims?
    ): ResponseEntity<String> {
        claims?.let { fileUploadServiceImpl!!.saveFile(file, it, "") }
        return ResponseEntity.ok("项目上传成功" + file.originalFilename)
    }
}
