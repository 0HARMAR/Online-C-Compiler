package com.example.loginservice.service;

import com.example.common.entity.User;
import com.example.loginservice.infrastructure.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public User addUser(User user) {
        userMapper.addUser(user);
        return user;
    }
}
