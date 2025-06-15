package com.example.loginservice.service;

import com.example.common.entity.User;
import com.example.common.utils.JwtUtils;
import com.example.loginservice.infrastructure.UserMapper;
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
        JwtUtils.id = Math.toIntExact(user.getId());
        JwtUtils.password = user.getPassword();
        JwtUtils.username = user.getName();
        String token = JwtUtils.generateJwt();
        user.setToken(token);
        userMapper.addUser(user);
        return user;
    }
}
