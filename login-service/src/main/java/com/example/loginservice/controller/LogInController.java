package com.example.loginservice.controller;

import com.example.common.entity.User;
import com.example.common.result.Result;
import com.example.loginservice.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
