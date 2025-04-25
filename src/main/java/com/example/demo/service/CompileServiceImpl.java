package com.example.demo.service;

import com.example.demo.common.JwtUtils;
import com.example.demo.configuration.FileStorageConfig;
import com.example.demo.dao.mapper.CompileTaskMapper;
import com.example.demo.dao.mapper.FileInfoMapper;
import com.example.demo.model.entity.CompileConfig;
import com.example.demo.model.entity.CompileTask;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class CompileServiceImpl implements CompileService{
    @Autowired
    FileInfoMapper fileInfoMapper;

    @Autowired
    CompileTaskMapper compileTaskMapper;

    public InputStreamResource preCompile(CompileConfig option, Claims claims) {
        String userName = JwtUtils.getUsername(claims);
        String compilePath = findFilePathByOwner(userName);
        String fileName = findFileNameByOwner(userName);

        // 创建新编译任务
        CompileTask compileTask = new CompileTask();
        String compileTaskId = UUID.randomUUID().toString();
        compileTask.setTaskId(compileTaskId);
        compileTaskMapper.addTask(compileTask);

        fileInfoMapper.updateFileTaskIdByFileId(findFileIdByOwner(userName),compileTaskId);

        startCompile(option,compilePath,getOutputFilePath(fileName,userName));

        InputStreamResource outputFile = null;
        try{
            outputFile = getOutputFile(userName,fileName);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return outputFile;
    }

    public void startCompile(CompileConfig option,String sourceFile,String outputFile) {
        try {
            int exitCode = startCompileProcess(sourceFile,outputFile,option);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static int startCompileProcess(String sourceFile, String outputFile,CompileConfig option) throws IOException, InterruptedException {
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

        return process.waitFor();
    }

    private String findFileIdByOwner(String owner){
        return fileInfoMapper.findFileIdByOwner(owner);
    }

    private String findFilePathByOwner(String userName) {
        return fileInfoMapper.findFilePathByOwner(userName);
    }

    private String findFileNameByOwner(String userName) {return fileInfoMapper.findFileNameByOwner(userName);}

    private String getOutputFilePath(String fileName,String userName) {
        String outputPath = FileStorageConfig.getOutputPath(userName);
        return outputPath+fileName;
    }
    public InputStreamResource getOutputFile(String userName,String fileName) throws IOException {
        File file = new File(getOutputFilePath(fileName,userName));

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return resource;
    }
}
