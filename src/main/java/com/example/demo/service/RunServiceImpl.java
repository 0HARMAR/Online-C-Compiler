package com.example.demo.service;

import com.example.demo.dao.mapper.FileInfoMapper;
import com.example.demo.util.FileDownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@Service
public class RunServiceImpl implements RunService {

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Override
    public String run(String args, String fileId, String token) {
        // 1. 通过 fileId 获取可执行文件的下载 URL
        String execFileUrl = fileInfoMapper.findFileByFileId(fileId);
        if (execFileUrl == null) {
            throw new RuntimeException("Executable file not found");
        }

        File execFile = null;
        try {
            // 2. 下载可执行文件到本地临时目录
            execFile = FileDownloadUtil.downloadFile(execFileUrl);
            // 3. 给文件加执行权限
            execFile.setExecutable(true);

            // 4. 构建运行命令
            String[] argArray = args == null || args.trim().isEmpty() ? new String[0] : args.split("\\s+");
            String[] command = new String[argArray.length + 1];
            command[0] = execFile.getAbsolutePath();
            System.arraycopy(argArray, 0, command, 1, argArray.length);

            // 5. 启动进程并收集输出
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
            process.waitFor();
            return output.toString();
        } catch (Exception e) {
            throw new RuntimeException("Run failed: " + e.getMessage(), e);
        } finally {
            if (execFile != null && execFile.exists()) {
                execFile.delete();
            }
        }
    }
}