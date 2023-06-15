package org.archguard.comate.cli

import io.github.cdimascio.dotenv.Dotenv
import org.archguard.comate.command.ComateCommand
import org.archguard.comate.command.CommandContext
import org.archguard.comate.connector.OPENAI_MODEL
import org.archguard.comate.connector.OpenAIConnector
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
    // todo: use clikt to parse command line arguments
    val cmd = if (args.isEmpty()) "检查 API 规范" else args[0]
    val language = "java"

    val basePath = Path(File(".").absolutePath)
    val comateCommand = textToComateCommand(cmd)

    logger.info("start execution ...")

    val openAiConnector = createConnector()
    val context = CommandContext(basePath, language, openAiConnector)

    val summarizePrompt = when (comateCommand) {
        ComateCommand.Intro -> ComateCommand.Intro.run(context)
        ComateCommand.LayeredStyle -> ComateCommand.LayeredStyle.run(context)
        ComateCommand.ApiGovernance -> ComateCommand.ApiGovernance.run(context)
        ComateCommand.FoundationGovernance -> ComateCommand.FoundationGovernance.run(context)

        // todo: thinking on split generate api into other command
        ComateCommand.ApiGen -> ComateCommand.ApiGen.run(context)

        // todo: handle command by LLM
        ComateCommand.None -> null
    } ?: return

    logger.info("summarize prompt text: $summarizePrompt")

    val output = openAiConnector.prompt(summarizePrompt)
    println(output)
}

private fun textToComateCommand(cmd: String): ComateCommand {
    val semantic = Semantic.create()
    val commandEmbedMap = createFunctionCallingEmbedding(semantic)

    val inputEmbed = semantic.embed(cmd)

    var comateCommand = ComateCommand.None
    run breaking@{
        commandEmbedMap.forEach { (command, embeds) ->
            embeds.forEach {
                try {
                    val similarity = cosineSimilarity(it, inputEmbed)
                    // todo: 1. make this threshold configurable a
                    // todo: 2. choose the command with highest similarity
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

