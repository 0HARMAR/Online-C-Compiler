package com.example.demo.controller;

import java.util.List;

import com.example.demo.common.Result;
import com.example.demo.dao.mapper.UserMapper;
import com.example.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.entity.*;;

@RestController
public class LogInController {
    @Autowired
    UserServiceImpl userServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<Result<User>> createUser(@RequestBody User user) {
        User userWithToken = userServiceImpl.addUser(user);
        return new ResponseEntity<>(Result.success(userWithToken), HttpStatus.OK);
    }
}
