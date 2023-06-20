package org.archguard.comate.server.action

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

enum class ToolingAction(val action: String) {
    INTRODUCE_SYSTEM("search") {

    }
    ;
}

@Serializable
data class ToolingThought(val thought: String, val action: ToolingAction, val actionInput: String)

@Serializable
data class ActionResult(val status: String, val action: String)

// https://gist.github.com/JurajBegovac/0007ae0a9631fe8606a48791c94ab6c6
fun Route.routeForAction() {
    post("/api/action/tooling") {
        val toolingThought = call.receive<ToolingThought>()
        println(toolingThought)


        call.respond(ActionResult("ok", ToolingAction.INTRODUCE_SYSTEM.toString()))
    }
}
