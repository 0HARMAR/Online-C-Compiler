package com.example.demo.controller

import com.example.demo.service.TerminalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 终端控制器，处理与终端相关的HTTP请求
 */
@RestController
class TerminalController {
    /**
     * 注入终端服务，用于处理终端的业务逻辑
     */
    @Autowired
    val terminalService: TerminalService? = null

    /**
     * 处理创建终端的POST请求
     * @param body 请求体内容，当前未使用
     */
    @PostMapping("/terminals")
    fun handleCreateTerminal(@RequestBody body: String) {
        terminalService?.createTerminal()
    }

    /**
     * 处理删除指定终端的POST请求
     * @param tid 终端ID
     * @param body 请求体内容，当前未使用
     */
    @PostMapping("/terminals/{tid}")
    fun handleDeleteTerminal(@PathVariable tid: String, @RequestBody body: String) {}
}
