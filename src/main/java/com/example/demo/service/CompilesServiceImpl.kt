package com.example.demo.service

import com.example.demo.common.JwtUtils
import com.example.demo.configuration.FileStorageConfig
import com.example.demo.dao.mapper.FileInfoMapper
import com.example.demo.model.entity.CompileConfig
import com.example.demo.util.FileDownloadUtil
import io.jsonwebtoken.Claims
import org.apache.hc.client5.http.auth.AuthStateCacheable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

@Service
class CompilesServiceImpl : CompilesService {
    @Autowired
    private lateinit var fileInfoMapper: FileInfoMapper

    @Throws(IOException::class)
    /**
     * project structure:
     * src/
     * include/
     * lib/
     * @return project structure map
     * like {"src":[file1, file2], "include":[file3,file4], "lib":[file5,file6]}
     */
    private fun uncompressProject(uploadsUrl: String): Pair<File, MutableMap<String, MutableList<String>>> {
        val directoryMap = mutableMapOf<String, MutableList<String>>()
        val projectZip: File = FileDownloadUtil.downloadFile(uploadsUrl)
        val tempDir = createTempDir(prefix = "project_")

        ZipFile(projectZip).use { zip ->
            val entries = zip.entries()
            while (entries.hasMoreElements()) {
                val entry = entries.nextElement()
                val outFile = File(tempDir, entry.name)
                if (entry.isDirectory) {
                    outFile.mkdirs()
                    continue
                } else {
                    outFile.parentFile.mkdirs()
                    zip.getInputStream(entry).use { input ->
                        outFile.outputStream().use { output -> input.copyTo(output) }
                    }
                }
                val dir = outFile.parentFile.relativeTo(tempDir).path.ifEmpty { "." }
                directoryMap.computeIfAbsent(dir) { mutableListOf() }.add(outFile.name)
            }
        }
        return Pair(tempDir, directoryMap)
    }

    override fun compiles(option: CompileConfig?, token: String?, fileId: String?): String? {
        val uploadsUrl = fileInfoMapper.findFileByFileId(fileId.toString())
        val (projectDir, projectMap) = uncompressProject(uploadsUrl)
        try {
            val command = constructCommand(option, projectDir)
            val process = ProcessBuilder(command)
                .directory(projectDir)
                .redirectErrorStream(true)
                .start()
            val output = process.inputStream.bufferedReader().readText()
            process.waitFor()
            return output
        } finally {
            projectDir.deleteRecursively()
        }
    }

    private fun constructCommand(option: CompileConfig?, projectDir: File): List<String> {
        val command = mutableListOf<String>()

        // 设置编译器类型，默认为 gcc
        val compiler = option?.compilerType ?: "gcc"
        command.add(compiler)

        // 添加 src 目录下的所有 .c 文件
        val srcDir = File(projectDir, "src")
        if (srcDir.exists() && srcDir.isDirectory) {
            srcDir.listFiles { file -> file.extension == "c" }?.forEach { file ->
                command.add(file.absolutePath)
            }
        }

        // 添加 include 目录
        val includeDir = File(projectDir, "include")
        if (includeDir.exists() && includeDir.isDirectory) {
            command.add("-I${includeDir.absolutePath}")
        }

        // 添加 lib 目录
        val libDir = File(projectDir, "lib")
        if (libDir.exists() && libDir.isDirectory) {
            command.add("-L${libDir.absolutePath}")
        }

        // 添加编译选项
        option?.compilerArgs?.split(" ")?.filter { it.isNotBlank() }?.let { args ->
            command.addAll(args)
        }

        // 设置输出文件名
        command.add("-o")
        command.add(File(projectDir, "output").absolutePath)

        return command
    }
}