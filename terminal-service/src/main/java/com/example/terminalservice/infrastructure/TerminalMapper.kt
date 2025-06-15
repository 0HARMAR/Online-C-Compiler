package com.example.demo.dao.mapper

import com.example.common.entity.Terminal
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TerminalMapper {
    @Insert("INSERT INTO terminal (tid, tname) VALUES (#{tid}, #{tname})")
    fun addTerminal(terminal: Terminal)
}