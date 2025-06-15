package com.example.demo.controller

import com.example.common.utils.JwtUtils
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap

open class TerminalWebSocketHandler(
) : TextWebSocketHandler() {

    private val sessions = ConcurrentHashMap<String, String>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val token = session.handshakeHeaders.getFirst("token")
        if (token != null) {
            val userId = JwtUtils.getUserId(JwtUtils.parseJwt(token))
            session.attributes["userId"] = userId
        }
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val userId = session.attributes["userId"] as? String
        val command = message.payload
        if (command.startsWith("sudo")) {
            session.sendMessage(TextMessage("Permission denied: 'sudo' commands are not allowed."))
            return
        }
        // 处理消息
        // 设置工作目录，terminals/{tid}
        val tid = userId ?: "default"
        val workingDir = "terminals/$tid"

        // 将命令按空格分隔
        val commandParts = command.split(" ")
        // 如果任何指令部分以/，～开头，则实际路径为workingDir + commandPart
        val fullCommand = commandParts.joinToString(" ") { part ->
            if (part.startsWith("/") || part.startsWith("~")) {
                "$workingDir/${part.removePrefix("/").removePrefix("~")}"
            } else {
                part
            }
        }

        // 在工作目录执行命令
        try {
            val process = ProcessBuilder(fullCommand.split(" "))
                .directory(java.io.File(workingDir))
                .redirectErrorStream(true)
                .start()
            val reader = process.inputStream.bufferedReader()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                session.sendMessage(TextMessage(line))
            }
            process.waitFor()
        } catch (e: Exception) {
            session.sendMessage(TextMessage("命令执行出错: ${e.message}"))
        }

    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {

    }
}