package com.example.demo.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.model.entity.CompileTask;

@Mapper
public interface CompileTaskMapper {
    @Select("select * from compile_task")
    public List<CompileTask> findAll();

    @Insert("insert into compile_task(task_id) values (#{taskId})")
    void addTask(CompileTask compileTask);

}
