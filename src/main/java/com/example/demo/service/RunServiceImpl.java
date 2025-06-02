package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class RunServiceImpl implements RunService {
    @Override
    public String run(String args) {
        // TODO: Implement the actual run logic here
        return "Run completed with args: " + args;
    }
} 