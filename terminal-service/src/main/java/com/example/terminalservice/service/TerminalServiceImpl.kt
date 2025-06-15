package com.example.terminalservice.service

import com.example.common.entity.Terminal
import com.example.common.utils.JwtUtils
import com.example.demo.dao.mapper.TerminalMapper
import com.example.demo.service.TerminalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File

@Service
class TerminalServiceImpl(
): TerminalService {
    @Autowired
    lateinit var  terminalMapper: TerminalMapper
    // 在terminals文件夹下新建一个以tid命名的文件夹
    override fun createTerminal(name: String,token: String) {
        val terminalsDir = File("terminals")
        if (!terminalsDir.exists()) {
            terminalsDir.mkdirs()
        }
        // 这里假设tid由某种方式生成或传入，这里用当前时间戳模拟
        val tid = JwtUtils.getUserId(JwtUtils.parseJwt(token))
        val terminalDir = File(terminalsDir, tid)
        if (!terminalDir.exists()) {
            terminalDir.mkdirs()
        }

        val userIdStr = JwtUtils.getUserId(JwtUtils.parseJwt(token))
        val userId = userIdStr.toLongOrNull()
        val terminal: Terminal = Terminal(userId, name)
        terminalMapper.addTerminal(terminal)
    }
}