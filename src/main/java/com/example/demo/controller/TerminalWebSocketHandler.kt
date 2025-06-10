package com.example.demo.controller

import com.example.demo.common.JwtUtils
import com.example.demo.service.DockerService
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap

open class TerminalWebSocketHandler(
) : TextWebSocketHandler() {

    private val sessions = ConcurrentHashMap<String, String>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val token = session.uri?.query?.split("&")
            ?.find { it.startsWith("token=") }
            ?.substringAfter("=")
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
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {

    }
}