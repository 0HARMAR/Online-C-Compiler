package com.example.demo.service;

import com.example.demo.common.JwtUtils;
import com.example.demo.configuration.FileStorageConfig;
import com.example.demo.model.entity.CompileConfig;
import com.example.demo.model.entity.CompileTask;
import io.jsonwebtoken.Claims;
import org.springframework.core.io.InputStreamResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class CompilesServiceImpl implements CompilesService {

    @Override
    public InputStreamResource handleCompiles(CompileConfig option, Claims claims) {
        Map<String, List<String>> projectList;
        // 解压项目文件
        try {
            projectList = uncompressProject(claims);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //开始编译任务
        try {
            startCompilesProcess(option,projectList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // TODO:需要返回项目编译后的可执行文件
        return null;
    }

    private Map<String, List<String>> uncompressProject(Claims claims) throws IOException {
        String userName = JwtUtils.getUsername(claims);
        String uploadsPath = FileStorageConfig.getUploadsPath(userName);
        File project = new File(uploadsPath);

        Map<String, List<String>> directoryMap = new HashMap<>();

        try (ZipFile zip = new ZipFile(project)) {
            Enumeration<? extends ZipEntry> entries = zip.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();

                if (entry.isDirectory()) {
                    continue; // 跳过目录条目
                }

                String fullPath = entry.getName();
                int lastSlashIndex = fullPath.lastIndexOf('/');

                String directory = "";
                String filename = fullPath;

                if (lastSlashIndex != -1) {
                    directory = fullPath.substring(0, lastSlashIndex);
                    filename = fullPath.substring(lastSlashIndex + 1);
                }

                // 将文件名添加到对应的目录列表中
                directoryMap
                        .computeIfAbsent(directory, k -> new ArrayList<>())
                        .add(filename);
            }
        }

        return directoryMap;
    }

    // gcc src/*.c -Iinclude -Llib -l你的库名 -Wall -o 可执行文件名
    private void startCompilesProcess(CompileConfig option,Map<String,List<String>> projectList) throws IOException {
        // TODO : 需要准备输出文件名和源文件名
        String outputFile = "";
        String sourceFile = "";
        Process process = new ProcessBuilder(
                option.getCompilerType(),
                option.getCompilerArgs(),
                "-o", outputFile,
                sourceFile
        ).redirectErrorStream(true).start();

        // 实时获取输出信息
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("[GCC] " + line);
        }
    }

}
