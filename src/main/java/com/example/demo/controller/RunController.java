package com.example.demo.controller;

import com.example.demo.common.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunController {
    @GetMapping("/run")
    public ResponseEntity<Result> HandelRun(
            @RequestParam("args") String args
    ) {
        return ResponseEntity.ok(Result.success("Run result"));
    }

}
