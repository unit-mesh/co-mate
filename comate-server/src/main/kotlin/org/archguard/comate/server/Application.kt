package org.archguard.comate.server

import io.ktor.server.application.*
import org.archguard.comate.server.plugins.configureRouting
import org.archguard.comate.server.plugins.configureSockets


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureRouting()
    configureSockets()
}