package org.archguard.comate.server.action

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

enum class ToolingAction(val action: String) {
    INTRODUCE_SYSTEM("search") {
        override fun toString(): String {
            return "Introduce System"
        }
    }
    ;
}

@Serializable
data class ToolingThought(val thought: String, val action: String, val actionInput: String)

// https://gist.github.com/JurajBegovac/0007ae0a9631fe8606a48791c94ab6c6
fun Route.routeForAction() {
    post("/api/action/tooling") {
        val toolingThought = call.receive<ToolingThought>()
        println(toolingThought)

        call.respondOutputStream(contentType = ContentType.Text.Any, status = HttpStatusCode.OK) {
            write("Hello".toByteArray())
            write("second\n".toByteArray())
        }
    }
}
