package org.archguard.comate.server.action

import io.github.cdimascio.dotenv.Dotenv
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.archguard.comate.command.fakeComateContext
import org.archguard.comate.connector.OPENAI_MODEL
import org.archguard.comate.connector.OpenAIConnector
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.pathString

val comateContext = fakeComateContext()

@Serializable
data class ToolingThought(val thought: String, val action: String, val actionInput: String)

@Serializable
data class ActionResult(val status: String, val action: String)

// https://gist.github.com/JurajBegovac/0007ae0a9631fe8606a48791c94ab6c6
fun Route.routeForAction() {
    post("/api/action/tooling") {
        val toolingThought = call.receive<ToolingThought>()

        if (comateContext.projectRepo == "") {
            val url: String
            try {
                url = parseUrlFromRequest(toolingThought.actionInput)
            } catch (e: Exception) {
                call.respond(ActionResult("error", "invalid url"))
                return@post
            }

            comateContext.projectRepo = url
        }

        // todo: use frontend connector
        if (comateContext.connector == null) {
            comateContext.connector = createConnector()
        }

        val action = ToolingAction.valueOf(toolingThought.action.uppercase())
        // parse url from toolingThought.actionInput
        val output = action.execute(comateContext.projectRepo)

        call.respond(ActionResult("ok", output.value.toString()))
    }
}

val regex = Regex("(?:https?|http)://(?:\\w+\\.)+\\w{2,}(?:\$|/[\\w-]+){2,}")

fun parseUrlFromRequest(input: String): String {
    val matchResult = regex.find(input) ?: throw Exception("invalid url")

    var url = matchResult.value

    if (url.endsWith(".") || url.endsWith("?") || url.endsWith("\"")) {
        url = url.dropLast(1)
    }

    return url
}

fun createConnector(): OpenAIConnector {
    val appDir = Paths.get(System.getProperty("user.home"), ".comate")
    val env = Paths.get(appDir.pathString, ".env")
    if (Files.notExists(appDir)) {
        Files.createDirectory(appDir)
        Files.createFile(env)
        Files.write(env, "OPENAI_API_KEY=xxx".toByteArray())
    }

    // for now, create ~/.comate/.env, and put OPENAI_API_KEY=... in it
    val dotenv = Dotenv.configure().directory(appDir.pathString).load()
    val apiKey = dotenv["OPENAI_API_KEY"]
    val apiProxy = dotenv["OPENAI_API_PROXY"] ?: null

    return OpenAIConnector(apiKey, OPENAI_MODEL[0], apiProxy)
}
