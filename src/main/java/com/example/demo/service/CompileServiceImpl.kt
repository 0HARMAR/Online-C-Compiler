package com.example.demo.service

import com.example.demo.common.JwtUtils
import com.example.demo.configuration.FileStorageConfig
import com.example.demo.dao.mapper.CompileTaskMapper
import com.example.demo.dao.mapper.FileInfoMapper
import com.example.demo.model.entity.CompileConfig
import com.example.demo.model.entity.CompileTask
import io.jsonwebtoken.Claims
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.stereotype.Service
import java.io.*
import java.util.*

@Service
class CompileServiceImpl : CompileService {
    @Autowired
    var fileInfoMapper: FileInfoMapper? = null

    @Autowired
    var compileTaskMapper: CompileTaskMapper? = null

    override fun compile(option: CompileConfig, token : String,fileId: String): String {
        val userName = JwtUtils.getUsername(JwtUtils.parseJwt(token))
        val compilePath = findFileByFileId(fileId)
        val fileName = findFileNameByOwner(userName)

        // 创建新编译任务
        val compileTask = CompileTask()
        val compileTaskId = UUID.randomUUID().toString()
        compileTask.taskId = compileTaskId
        compileTaskMapper!!.addTask(compileTask)

        fileInfoMapper!!.updateFileTaskIdByFileId(findFileIdByOwner(userName), compileTaskId)

        startCompile(option, compilePath, getOutputFilePath(fileName, userName))

        var outputFile: InputStreamResource? = null
        try {
            outputFile = getOutputFile(userName, fileName)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return outputFile
    }

    private fun findFileByFileId(fileId: String) {
        fileInfoMapper.findFileByFileId(fileId)
    }

    fun startCompile(option: CompileConfig, sourceFile: String?, outputFile: String?) {
        try {
            val exitCode = startCompileProcess(sourceFile, outputFile, option)
        } catch (e: IOException) {
            throw RuntimeException(e)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }

    private fun findFileIdByOwner(owner: String): String {
        return fileInfoMapper!!.findFileIdByOwner(owner)
    }

    private fun findFilePathByOwner(userName: String): String {
        return fileInfoMapper!!.findFilePathByOwner(userName)
    }

    private fun findFileNameByOwner(userName: String): String {
        return fileInfoMapper!!.findFileNameByOwner(userName)
    }

    private fun getOutputFilePath(fileName: String, userName: String): String {
        val outputPath = FileStorageConfig.getOutputPath(userName)
        return outputPath + fileName
    }

    @Throws(IOException::class)
    fun getOutputFile(userName: String, fileName: String): InputStreamResource {
        val file = File(getOutputFilePath(fileName, userName))

        val resource = InputStreamResource(FileInputStream(file))
        return resource
    }

    companion object {
        @Throws(IOException::class, InterruptedException::class)
        fun startCompileProcess(sourceFile: String?, outputFile: String?, option: CompileConfig): Int {
            val process = ProcessBuilder(
                option.compilerType,
                option.compilerArgs,
                "-o", outputFile,
                sourceFile
            ).redirectErrorStream(true).start()

            // 实时获取输出信息
            val reader = BufferedReader(
                InputStreamReader(process.inputStream)
            )
            var line: String
            while ((reader.readLine().also { line = it }) != null) {
                println("[GCC] $line")
            }

            return process.waitFor()
        }
    }
}
