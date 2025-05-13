package com.example.demo.controller

import com.example.demo.service.DockerService
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap

open class TerminalWebSocketHandler(
    private val dockerService: DockerService
) : TextWebSocketHandler() {

    private val sessions = ConcurrentHashMap<String, String>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        // 创建容器并启动
        val containerId = dockerService.createContainer("ubuntu:latest")
        dockerService.startContainer(containerId)
        sessions[session.id] = containerId
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val containerId = sessions[session.id]!!
        val output = dockerService.execCommand(containerId, message.payload)
        session.sendMessage(TextMessage(output))
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        // 清理容器（可选）
        sessions[session.id]?.let { dockerService.stopContainer(it) }
        sessions.remove(session.id)
    }
}