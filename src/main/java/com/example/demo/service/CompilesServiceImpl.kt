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
    private fun uncompressProject(uploadsUrl: String): MutableMap<String?, MutableList<String?>?> {
        val directoryMap: MutableMap<String?, MutableList<String?>?> = HashMap<String?, MutableList<String?>?>()
        val project: File = FileDownloadUtil.downloadFile(uploadsUrl)

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

    override fun compiles(
        option: CompileConfig?,
        token: String?,
        fileId: String?
    ): String? {
        val uploadsUrl: String = fileInfoMapper.findFileByFileId(fileId.toString())
        val projectMap: MutableMap<String?, MutableList<String?>?> = uncompressProject(uploadsUrl)


    }

    // Demo : gcc src/*.c -Iinclude -Llib  -o output
    private fun constructCommand(option: CompileConfig?,projectMap: MutableMap<String?, MutableList<String?>?>?): String {
        // TODO: 根据编译选项和项目结构构建编译命令
        return TODO("Provide the return value")
    }
}