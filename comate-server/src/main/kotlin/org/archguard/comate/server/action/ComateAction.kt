package org.archguard.comate.server.action

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.archguard.comate.command.fakeComateContext
import org.archguard.comate.dynamic.functions.FunctionResult
import org.archguard.comate.dynamic.functions.InitializeSystemFunction
import org.archguard.comate.dynamic.functions.IntroduceSystemFunction
import org.archguard.comate.dynamic.functions.RestApiGovernanceFunction

val comateContext = fakeComateContext()

enum class ToolingAction(val action: String) {
    INTRODUCE_SYSTEM(action = "introduce_system") {
        override fun execute(input: String): FunctionResult.Success<String> {
            comateContext.projectRepo = input
            InitializeSystemFunction(comateContext).execute()
            return IntroduceSystemFunction(comateContext).execute()
        }
    },
    REST_API_GOVERNANCE(action = "rest_api_governance") {
        override fun execute(input: String): FunctionResult.Success<Any> {
            return RestApiGovernanceFunction(comateContext).execute()
        }
    },
    ;

    abstract fun execute(input: String): FunctionResult.Success<Any>
}

@Serializable
data class ToolingThought(val thought: String, val action: String, val actionInput: String)

@Serializable
data class ActionResult(val status: String, val action: String)

// https://gist.github.com/JurajBegovac/0007ae0a9631fe8606a48791c94ab6c6
fun Route.routeForAction() {
    post("/api/action/tooling") {
        val toolingThought = call.receive<ToolingThought>()

        if (comateContext.projectRepo == "") {
            var url: String
            try {
                url = Regex("https?://github.com/([^/]+)/([^/]+)").find(toolingThought.actionInput)!!.groupValues[0]
                if (url.endsWith(".") || url.endsWith("?") || url.endsWith("\"")) {
                    url = url.dropLast(1)
                }
            } catch (e: Exception) {
                call.respond(ActionResult("error", "invalid url"))
                return@post
            }

            comateContext.projectRepo = url
        }

        val action = ToolingAction.valueOf(toolingThought.action.uppercase())
        // parse url from toolingThought.actionInput
        val output = action.execute(comateContext.projectRepo)

        call.respond(ActionResult("ok", output.value.toString()))
    }
}
