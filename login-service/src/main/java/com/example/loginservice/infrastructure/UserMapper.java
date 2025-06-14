package com.example.loginservice.infrastructure;

import com.example.common.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("insert into users(name,password,token) values (#{name},#{password},#{token})")
    public void addUser(User user);
}
