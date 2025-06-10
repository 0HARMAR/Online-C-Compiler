package com.example.demo.service

interface TerminalService {
    /**
     * create a terminal
     */
    fun createTerminal(token: String)

    /**
     * interact with a terminal
     */
}