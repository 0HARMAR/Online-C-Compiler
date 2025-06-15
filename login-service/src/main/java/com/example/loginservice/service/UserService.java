package com.example.loginservice.service;

import com.example.common.entity.User;

public interface UserService {
    User addUser(User user);

    String generateUserToken();
}
