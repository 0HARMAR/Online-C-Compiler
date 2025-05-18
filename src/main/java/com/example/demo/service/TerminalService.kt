package com.example.demo.service

interface TerminalService {
    /**
     * create a terminal
     */
    fun createTerminal()

    /**
     * interact with a terminal
     */
    fun interactWithTerminal(terminalId: String,command: String): String
}