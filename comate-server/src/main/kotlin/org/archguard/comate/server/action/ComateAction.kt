package org.archguard.comate.server.action

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.archguard.comate.command.fakeComateContext
import org.archguard.comate.dynamic.functions.InitializeSystem

val context = fakeComateContext()

enum class ToolingAction(val action: String) {
    INTRODUCE_SYSTEM(action = "introduce_system") {
        override fun execute(input: String): String {
            InitializeSystem(context).execute()
            return ""
        }
    }
    ;

    abstract fun execute(input: String): String
}

@Serializable
data class ToolingThought(val thought: String, val action: String, val actionInput: String)

@Serializable
data class ActionResult(val status: String, val action: String)

// https://gist.github.com/JurajBegovac/0007ae0a9631fe8606a48791c94ab6c6
fun Route.routeForAction() {
    post("/api/action/tooling") {
        val toolingThought = call.receive<ToolingThought>()

        val url = Regex("https?://github.com/([^/]+)/([^/]+)").find(toolingThought.actionInput)!!.groupValues[0]

        val action = ToolingAction.valueOf(toolingThought.action.uppercase())
        // parse url from toolingThought.actionInput
        action.execute(url)

        call.respond(ActionResult("ok", ToolingAction.INTRODUCE_SYSTEM.toString()))
    }
}
