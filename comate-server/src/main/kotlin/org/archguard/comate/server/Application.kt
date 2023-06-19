package org.archguard.comate.server

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.archguard.comate.server.prompt.routeByPrompt
import org.archguard.comate.server.socket.configureSockets


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    routing {
        routeByPrompt()
    }
    configureSockets()
}