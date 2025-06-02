package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.service.RunService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunController {
    private final RunService runService;

    public RunController(RunService runService) {
        this.runService = runService;
    }

    @GetMapping("/run")
    public ResponseEntity<Result> HandelRun(
            @RequestParam("args") String args
    ) {
        String result = runService.run(args);
        return ResponseEntity.ok(Result.success(result));
    }
}
