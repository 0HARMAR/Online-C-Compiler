package com.example.compileservice.service

import com.example.common.entity.CompileConfig
import com.example.common.entity.CompileTask
import com.example.common.result.CompileResult
import com.example.common.result.UploadResult
import com.example.common.utils.FileDownloadUtil
import com.example.common.utils.JwtUtils
import com.example.uploadservice.service.FileUploadService
import com.example.compileservice.infrastructure.CompileTaskMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Service
import java.io.*
import java.util.*
import java.time.LocalDateTime
import com.example.uploadservice.infrastructure.FileInfoMapper

@Service
class CompileServiceImpl : CompileService {
    @Autowired
    private lateinit var fileUploadService: FileUploadService

    @Autowired
    private lateinit var fileInfoMapper: FileInfoMapper

    @Autowired
    private lateinit var compileTaskMapper: CompileTaskMapper

    override fun compile(option: CompileConfig, token: String, fileId: String): CompileResult? {
        val userName = JwtUtils.getUsername(JwtUtils.parseJwt(token))
        val sourceFileUrl = fileInfoMapper.findFileByFileId(fileId)
            ?: throw RuntimeException("File not found")

        // 创建新编译任务
        val compileTask = CompileTask().apply {
            taskId = UUID.randomUUID().toString()
            status = CompileTask.Status.pending
            createdAt = LocalDateTime.now()
        }
        compileTaskMapper.addTask(compileTask)

        // 更新文件任务ID
        fileInfoMapper.updateFileTaskIdByFileId(fileId, compileTask.taskId)

        try {
            // 下载源文件并编译
            val result = startCompile(option, sourceFileUrl,token)
            compileTask.status = CompileTask.Status.compiling            
            return result
        } catch (e: Exception) {
            compileTask.status = CompileTask.Status.failed
            throw e
        }
    }

    // 返回编译结果
    private fun startCompile(option: CompileConfig, sourceFileUrl: String?,token: String): CompileResult {
        if (sourceFileUrl == null) {
            throw IllegalArgumentException("Source file URL cannot be null")
        }

        var sourceFile: File? = null
        var outputFile: File? = null
        try {
            // 下载源文件到临时目录
            sourceFile = FileDownloadUtil.downloadFile(sourceFileUrl)
            
            // 创建输出文件
            outputFile = File(sourceFile.parent, sourceFile.nameWithoutExtension)
            
            // 构建编译命令
            val command = mutableListOf<String>()
            command.add((option.compilerType?.toString() ?: "gcc"))
            command.addAll(option.compilerArgs?.split(" ")?.filter { it.isNotBlank() } ?: emptyList())
            command.add("-o")
            command.add(outputFile.absolutePath)
            command.add(sourceFile.absolutePath)

            println(command)
            // 启动编译进程
            val process = ProcessBuilder(command)
                .redirectErrorStream(true)
                .start()

            // 收集编译输出
            val output = StringBuilder()
            BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    output.append(line).append("\n")
                }
            }

            // 等待编译完成
            val exitCode = process.waitFor()

            // 将编译结果上传至OSS并保存文件信息
            val uploadResult: UploadResult = fileUploadService.saveFile(outputFile,token)
            return CompileResult(
                 exitCode,
                uploadResult.uploadUrl,
                uploadResult.fileId
            )
        } finally {
            // 清理临时文件
            sourceFile?.delete()
            outputFile?.let { it.delete() }
        }
    }
}
