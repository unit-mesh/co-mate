package org.archguard.comate.server.action

import io.github.cdimascio.dotenv.Dotenv
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.archguard.comate.command.fakeComateContext
import org.archguard.comate.connector.OPENAI_MODEL
import org.archguard.comate.connector.OpenAIConnector
import org.archguard.comate.server.action.dto.ActionResult
import org.archguard.comate.server.action.dto.ToolingThought
import org.archguard.spec.lang.FoundationSpec
import org.archguard.spec.runtime.KotlinInterpreter
import org.archguard.spec.runtime.interpreter.api.InterpreterRequest
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.pathString

val comateContext = fakeComateContext()

const val mvcDsl = """foundation {
    project_name {
        pattern("^([a-z0-9-]+)-([a-z0-9-]+)(-common)?\${'$'}")
        example("system1-webapp1")
    }

    layered {
        layer("controller") {
            pattern(".*\\.controller") { name shouldBe endsWith("Controller") }
        }
        layer("service") {
            pattern(".*\\.service") {
                name shouldBe endsWith("DTO", "Request", "Response", "Factory", "Service")
            }
        }
        layer("repository") {
            pattern(".*\\.repository") { name shouldBe endsWith("Entity", "Repository", "Mapper") }
        }

        dependency {
            "controller" dependedOn "service"
            "controller" dependedOn "repository"
            "service" dependedOn "repository"
        }
    }

    naming {
        class_level {
            style("CamelCase")
            pattern(".*") { name shouldNotBe contains("${'$'}") }
        }
        function_level {
            style("CamelCase")
            pattern(".*") { name shouldNotBe contains("${'$'}") }
        }
    }
}"""

val defaultImports = """import org.archguard.spec.lang.*
import org.archguard.spec.lang.base.*
import org.archguard.spec.lang.domain.*
import org.archguard.spec.lang.foundation.*
import org.archguard.spec.lang.matcher.*
import org.archguard.spec.lang.restapi.*
import org.archguard.spec.lang.architecture.*
import org.archguard.spec.lang.caseflow.*
"""


// we still need time to thinking about: should we use magic string here?
// like: %comate
val mvcFoundation: String = """
$defaultImports

$mvcDsl
"""

fun Route.routeForAction() {
    val repl = KotlinInterpreter()
    val mvcDslSpec = repl.evalCast<FoundationSpec>(InterpreterRequest(code = mvcFoundation))

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

        val action = ComateToolingAction.from(toolingThought.action.lowercase())
        if (action == null) {
            call.response.status(HttpStatusCode.BadRequest)
            return@post
        }

        // todo: should be load from somewhere, like Database
        if (action == ComateToolingAction.FOUNDATION_SPEC_GOVERNANCE) {
            comateContext.spec = mvcDslSpec
        }

        val output = action.execute(comateContext)

        call.respond(ActionResult("ok", output.value.toString()))
    }
}

val regex = Regex("(?:https?|http|git)://(?:\\w+\\.)+\\w{2,}(?:\$|/[\\w-]+){2,}")

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
