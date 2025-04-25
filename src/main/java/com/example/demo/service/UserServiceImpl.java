package com.example.demo.service;

import com.example.demo.common.JwtUtils;
import com.example.demo.configuration.FileStorageConfig;
import com.example.demo.dao.mapper.UserMapper;
import com.example.demo.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
     public String generateUserToken() {
        return JwtUtils.generateJwt();
    }

    @Override
    public User addUser(User user) {
        String token = JwtUtils.generateJwt();
        user.setToken(token);
        userMapper.addUser(user);
        return user;
    }
}
