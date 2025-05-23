package com.example.demo.service

import com.example.demo.common.JwtUtils
import com.example.demo.configuration.FileStorageConfig
import com.example.demo.model.entity.CompileConfig
import io.jsonwebtoken.Claims
import org.springframework.core.io.InputStreamResource
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class CompilesServiceImpl : CompilesService {
    override fun handleCompiles(option: CompileConfig, claims: Claims): InputStreamResource? {
        val projectList: MutableMap<String?, MutableList<String?>?>?
        // 解压项目文件
        try {
            projectList = uncompressProject(claims)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

        //开始编译任务
        try {
            startCompilesProcess(option, projectList)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

        // TODO: 返回编译后的可执行文件,使用aliyunOSS
        val outputFile = File("output")
        if (outputFile.exists()) {
            return InputStreamResource(outputFile.inputStream())
        }
        return null
    }

    @Throws(IOException::class)
    private fun uncompressProject(claims: Claims): MutableMap<String?, MutableList<String?>?> {
        val userName = JwtUtils.getUsername(claims)
        val uploadsPath = FileStorageConfig.getUploadsPath(userName)
        val project = File(uploadsPath)

        val directoryMap: MutableMap<String?, MutableList<String?>?> = HashMap<String?, MutableList<String?>?>()

        ZipFile(project).use { zip ->
            val entries = zip.entries()
            while (entries.hasMoreElements()) {
                val entry: ZipEntry = entries.nextElement()

                if (entry.isDirectory()) {
                    continue  // 跳过目录条目
                }

                val fullPath = entry.getName()
                val lastSlashIndex = fullPath.lastIndexOf('/')

                var directory = ""
                var filename = fullPath

                if (lastSlashIndex != -1) {
                    directory = fullPath.substring(0, lastSlashIndex)
                    filename = fullPath.substring(lastSlashIndex + 1)
                }

                // 将文件名添加到对应的目录列表中
                directoryMap
                    .computeIfAbsent(directory) { k: kotlin.String? -> java.util.ArrayList<kotlin.String?>() }!!
                    .add(filename)
            }
        }
        return directoryMap
    }

    @Throws(IOException::class)
    private fun startCompilesProcess(option: CompileConfig, projectList: MutableMap<String?, MutableList<String?>?>?) {
        // 准备输出文件名，假设固定为 output 可执行文件
        val outputFile = "output"

        // 拼接所有源文件路径，格式为 "目录/文件名"
        val sourceFiles = mutableListOf<String>()
        projectList?.forEach { (directory, files) ->
            files?.forEach { filename ->
                val path = if (directory.isNullOrEmpty()) filename else "$directory/$filename"
                path?.let { sourceFiles.add(it) }
            }
        }
        // 将所有源文件用空格连接
        val sourceFile = sourceFiles.joinToString(" ")

        // 构造编译命令参数列表
        val command = mutableListOf<String>()
        option.compilerType?.let { command.add(it) }
        val compilerArgs = option.compilerArgs
        if (!compilerArgs.isNullOrBlank()) {
            command.addAll(compilerArgs.split("\\s+".toRegex()))
        }
        command.add("-o")
        command.add(outputFile)
        command.addAll(sourceFiles)

        val process = ProcessBuilder(command)
            .redirectErrorStream(true)
            .start()

        // 实时获取输出信息
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            println("[GCC] $line")
        }
    }
}