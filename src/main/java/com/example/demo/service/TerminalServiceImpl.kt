package com.example.demo.service

import com.example.demo.common.JwtUtils
import io.jsonwebtoken.Jwt
import org.springframework.stereotype.Service

@Service
class TerminalServiceImpl(
):TerminalService {
    // 在terminals文件夹下新建一个以tid命名的文件夹
    override fun createTerminal(token: String) {
        val terminalsDir = java.io.File("terminals")
        if (!terminalsDir.exists()) {
            terminalsDir.mkdirs()
        }
        // 这里假设tid由某种方式生成或传入，这里用当前时间戳模拟
        val tid = JwtUtils.getUserId(JwtUtils.parseJwt(token))
        val terminalDir = java.io.File(terminalsDir, tid)
        if (!terminalDir.exists()) {
            terminalDir.mkdirs()
        }
    }
}