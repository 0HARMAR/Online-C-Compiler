package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

@RestController
@RequestMapping("/command")
public class CommandController {

    private static final Logger logger = LoggerFactory.getLogger(CommandController.class);

    @PostMapping("/execute")
    public String executeCommand(@RequestBody CommandRequest commandRequest) {
        String command = commandRequest.getCommand();
        logger.info("Executing command: {}", command);
        StringBuilder output = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                logger.info("Command executed successfully. Output:\n{}", output.toString());
                return "Command executed successfully:\n" + output.toString();
            } else {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String errorLine;
                StringBuilder errorOutput = new StringBuilder();
                while ((errorLine = errorReader.readLine()) != null) {
                    errorOutput.append(errorLine).append("\n");
                }
                logger.error("Command execution failed with exit code {}. Error:\n{}", exitVal, errorOutput.toString());
                return "Command execution failed with exit code " + exitVal + ":\n" + errorOutput.toString();
            }

        } catch (IOException e) {
            logger.error("IOException while executing command: {}", e.getMessage());
            return "Error executing command: " + e.getMessage();
        } catch (InterruptedException e) {
            logger.error("InterruptedException while executing command: {}", e.getMessage());
            Thread.currentThread().interrupt(); // Restore the interrupted status
            return "Error executing command: " + e.getMessage();
        }
    }
}

class CommandRequest {
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
} 