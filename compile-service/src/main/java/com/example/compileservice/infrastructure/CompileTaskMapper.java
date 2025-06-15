package com.example.compileservice.infrastructure;

import com.example.common.entity.CompileTask;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CompileTaskMapper {
    @Select("select * from compile_task")
    public List<CompileTask> findAll();

    @Insert("insert into compile_task(task_id) values (#{taskId})")
    void addTask(CompileTask compileTask);

}
