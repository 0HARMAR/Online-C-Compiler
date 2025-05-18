package com.example.demo.controller

import com.example.demo.service.TerminalService
import jdk.internal.org.jline.terminal.impl.jna.win.JnaWinSysTerminal.createTerminal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TerminalController {
    @Autowired
    val terminalService: TerminalService? = null

    @PostMapping("/terminals")
    fun handleCreateTerminal(@RequestBody body: String) {
        terminalService?.createTerminal()
    }

    @PostMapping("/terminals/{tid}")
    fun handleDeleteTerminal(@PathVariable tid: String, @RequestBody body: String) {}
}