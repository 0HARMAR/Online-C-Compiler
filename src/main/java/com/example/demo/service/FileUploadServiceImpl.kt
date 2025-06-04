package com.example.demo.service

import com.example.demo.common.AliyunOSSOperator
import com.example.demo.common.JwtUtils
import com.example.demo.configuration.FileStorageConfig
import com.example.demo.dao.mapper.FileInfoMapper
import com.example.demo.exception.BusinessException
import com.example.demo.exception.ErrorCode
import com.example.demo.exception.HashCalculationException
import com.example.demo.model.entity.FileInfo
import io.jsonwebtoken.Claims
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.security.MessageDigest
import java.time.LocalDateTime
import java.util.*

@Service
class FileUploadServiceImpl : FileUploadService {
    @Autowired
    private val fileInfoMapper: FileInfoMapper? = null

    override fun saveFile(file: MultipartFile, token: String): String {
        // 检查文件是否为空
        if (file.isEmpty) {
            throw BusinessException(ErrorCode.FILE_NOT_FOUND)
        }

        val fileInfo = FileInfo()
        // 获取fileId
        val fileId = UUID.randomUUID().toString()
        // 获取fileType
        val fileType = getFileType(file)
        // 获取fileName
        val fileName = file.originalFilename
        // 获取fileSize
        val fileSize = file.size
        // 获取hash值
        val hashSha256: String
        try {
            hashSha256 = calculateSHA256(file.bytes)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        // 获取encoding
        var encoding = "utf8" // 默认编码为UTF-8
        when (encoding) {
            "utf8" -> fileInfo.encoding = FileInfo.Encoding.UTF_8
            "gbk" -> fileInfo.encoding = FileInfo.Encoding.GBK
            "ascii" -> fileInfo.encoding = FileInfo.Encoding.ASCII
        }
        // 获取createAt
        fileInfo.createdAt = LocalDateTime.now()
        // 获取updateAt
        fileInfo.updatedAt = LocalDateTime.now()
        // 获取所有者
        val userName = JwtUtils.getUsername(JwtUtils.parseJwt(token))
        fileInfo.owner = userName

        fileInfo.fileId = fileId
        fileInfo.fileType = fileType
        fileInfo.fileSize = fileSize
        fileInfo.fileName = fileName
        fileInfo.hashSha256 = hashSha256
        fileInfoMapper!!.addFile(fileInfo)

        // 将文件保存到OSS
        val content : ByteArray = file.bytes
        val uploadUrl = AliyunOSSOperator.upload(content,fileName)

        return uploadUrl
    }

    private fun getFileType(file: MultipartFile): FileInfo.FileType {
        val originalFilename = file.originalFilename
        val fileExtension = StringUtils.getFilenameExtension(originalFilename)
        val fileType = determineFileType(fileExtension!!)
        return fileType
    }

    private fun determineFileType(extension: String): FileInfo.FileType {
        if (extension == null) throw BusinessException(ErrorCode.FILE_NOT_ALLOWED)

        return when (extension.lowercase(Locale.getDefault())) {
            "c" -> FileInfo.FileType.C_SOURCE
            "h" -> FileInfo.FileType.HEADER
            "a" -> FileInfo.FileType.STATIC_LIB
            "so" -> FileInfo.FileType.SHARED_LIB
            "o" -> FileInfo.FileType.OBJECT
            "mk", "makefile" -> FileInfo.FileType.MAKEFILE
            else -> throw BusinessException(ErrorCode.FILE_NOT_ALLOWED)
        }
    }

    // SHA256计算方法
    private fun calculateSHA256(data: ByteArray): String {
        try {
            val digest = MessageDigest.getInstance("SHA-256")
            val hashBytes = digest.digest(data)
            return bytesToHex(hashBytes)
        } catch (e: Exception) {
            throw HashCalculationException()
        }
    }

    companion object {
        // 字节转十六进制
        private fun bytesToHex(hash: ByteArray): String {
            val hexString = StringBuilder()
            for (b in hash) {
                val hex = Integer.toHexString(0xff and b.toInt())
                if (hex.length == 1) hexString.append('0')
                hexString.append(hex)
            }
            return hexString.toString()
        }
    }
}
