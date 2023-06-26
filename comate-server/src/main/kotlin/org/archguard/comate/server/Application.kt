package org.archguard.comate.server

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.archguard.comate.server.action.routeForAction
import org.archguard.comate.server.prompt.routeByPrompt


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(CORS)
    install(Compression)

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    routing {
        // don't remove this route, it's for test server online.
        get("/") {
            call.respondText("Hello, world!")
        }
        routeByPrompt()
        routeForAction()
    }
}