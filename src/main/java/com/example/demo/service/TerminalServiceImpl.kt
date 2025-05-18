package com.example.demo.service

import org.springframework.stereotype.Service

@Service
class TerminalServiceImpl(
    private val dockerService: DockerService
):TerminalService {
    override fun createTerminal() {
        if(dockerService.haveUbuntuImage()){
            val terminalId :String = dockerService.findOrCreateUbuntuContainer()

        }
        else{
            TODO()
        }
    }

    override fun interactWithTerminal(terminalId: String, command: String): String {
        return dockerService.interactWithSession(terminalId, command)
    }


}