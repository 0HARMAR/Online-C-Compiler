package com.example.loginservice.infrastructure;

import com.example.common.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("insert into users(name,password) values (#{name},#{password})")
    void addUser(User user);
}
