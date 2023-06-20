package org.archguard.comate.server

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.archguard.comate.server.action.routeForAction
import org.archguard.comate.server.prompt.routeByPrompt
import org.archguard.comate.server.socket.configureSockets


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
//    install(ContentNegotiation) {
//        json(Json {
//            prettyPrint = true
//            isLenient = true
//        })
//    }

    routing {
        get("/") {
            call.respondText("Hello")
        }
        routeByPrompt()
        routeForAction()
    }
    configureSockets()
}