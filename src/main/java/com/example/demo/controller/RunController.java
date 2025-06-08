package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.service.RunService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunController {
    private final RunService runService;

    public RunController(RunService runService) {
        this.runService = runService;
    }

    @GetMapping("/run")
    public ResponseEntity<Result<String>> handleRun(
            @RequestParam("args") String args,
            @RequestParam("fileId") String fileId,
            @RequestHeader("token") String token
    ) {
        String result = runService.run(args, fileId, token);
        return ResponseEntity.ok(Result.success(result));
    }
}