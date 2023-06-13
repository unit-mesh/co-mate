package org.archguard.comate.cli

import io.github.cdimascio.dotenv.Dotenv
import org.archguard.comate.action.ComateCommand
import org.archguard.comate.action.CommandContext
import org.archguard.comate.smart.OPENAI_MODEL
import org.archguard.comate.smart.OpenAIConnector
import org.archguard.comate.smart.Semantic
import org.archguard.comate.smart.cosineSimilarity
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.logging.Logger
import kotlin.io.path.Path
import kotlin.io.path.pathString

private val logger = Logger.getLogger("comate")

fun main(args: Array<String>) {
    val cmd = if (args.isEmpty()) "检查 API 规范" else args[0]

//
//    val eval = KotlinInterpreter().eval(
//        InterpreterRequest(
//            code = """
//import org.archguard.meta.dsl.*
//
//val governance = rest_api {
//    uri_construction {
//        rule("\\/api\\/[a-zA-Z0-9]+\\/v[0-9]+\\/[a-zA-Z0-9\\/\\-]+")
//        example("/api/petstore/v1/pets/dogs")
//    }
//
//    http_action("GET", "POST", "PUT", "DELETE")
//    status_code(200, 201, 202, 204, 400, 401, 403, 404, 500, 502, 503, 504)
//
//    security(
//        ""${'"'}
//Token Based Authentication (Recommended) Ideally, microservices should be stateless so the service instances can be scaled out easily and the client requests can be routed to multiple independent service providers. A token based authentication mechanism should be used instead of session based authentication
//""${'"'}.trimIndent()
//    )
//
//    misc(""${'"'}${'"'}${'"'}${'"'})
//}
//
//    """.trimIndent()
//        )
//    )
//    println(eval)

    val basePath = Path(File(".").absolutePath)
    val comateCommand = cmdToComateCommand(cmd)

    logger.info("start execution ...")

    val language = "java"
    val openAiConnector = createConnector()
    val context = CommandContext(basePath, language, openAiConnector)
    val summarizePrompt = when (comateCommand) {
        ComateCommand.Intro -> ComateCommand.Intro.run(context)
        ComateCommand.LayeredStyle -> ComateCommand.LayeredStyle.run(context)
        ComateCommand.ApiGovernance -> ComateCommand.ApiGovernance.run(context)
        ComateCommand.FoundationGovernance -> ComateCommand.FoundationGovernance.run(context)
        ComateCommand.ApiGen -> ComateCommand.ApiGen.run(context)
        // todo: handle command by LLM
        ComateCommand.None -> null
    } ?: return

    logger.info("summarize prompt text: $summarizePrompt")

    val output = openAiConnector.prompt(summarizePrompt)
    println(output)
}

private fun cmdToComateCommand(cmd: String): ComateCommand {
    val semantic = Semantic.create()
    val commandEmbedMap = createEmbeddingMap(semantic)

    val inputEmbed = semantic.embed(cmd)

    var comateCommand = ComateCommand.None
    run breaking@{
        commandEmbedMap.forEach { (command, embeds) ->
            embeds.forEach {
                try {
                    val similarity = cosineSimilarity(it, inputEmbed)
                    if (similarity > 0.6) {
                        comateCommand = command
                        return@breaking
                    }
                } catch (e: Exception) {
//                println(e)
                }
            }
        }
    }

    return comateCommand
}

fun createConnector(): OpenAIConnector {
    val appDir = Paths.get(System.getProperty("user.home"), ".comate")
    val env = Paths.get(appDir.pathString, ".env")
    if (Files.notExists(appDir)) {
        Files.createDirectory(appDir)
        Files.createFile(env)
        logger.warning("please put OPENAI_API_KEY=xxx in ~/.comate/.env")
        Files.write(env, "OPENAI_API_KEY=xxx".toByteArray())
    }

    // for now, create ~/.comate/.env, and put OPENAI_API_KEY=... in it
    val dotenv = Dotenv.configure().directory(appDir.pathString).load()
    val apiKey = dotenv["OPENAI_API_KEY"]
    val apiProxy = dotenv["OPENAI_API_PROXY"] ?: null

    return OpenAIConnector(apiKey, OPENAI_MODEL[0], apiProxy)
}

