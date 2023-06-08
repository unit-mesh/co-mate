package org.archguard.comate.cli

import io.github.cdimascio.dotenv.Dotenv
import org.archguard.comate.action.ComateCommand
import org.archguard.comate.action.CommandContext
import org.archguard.comate.smart.OPENAI_MODEL
import org.archguard.comate.smart.OpenAIConnector
import org.archguard.comate.smart.Semantic
import org.archguard.comate.smart.cosineSimilarity
import java.io.File
import java.util.logging.Logger
import kotlin.io.path.Path
import kotlin.io.path.pathString
import java.nio.file.Files
import java.nio.file.Paths

typealias Embed = FloatArray

private val logger = Logger.getLogger("comate")

fun main(args: Array<String>) {
    val cmd = if (args.isEmpty()) "检查 API 规范" else args[0]

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
        ComateCommand.None -> null
    } ?: return

    logger.info("summarize prompt text: $summarizePrompt")

    val output = openAiConnector.prompt(summarizePrompt)
    println(output)
}

private fun cmdToComateCommand(cmd: String): ComateCommand {
    val semantic = Semantic.create()
    val commandEmbedMap = createEmbedMap(semantic)

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
        Files.write(env, "OPENAI_API_KEY=xxx".toByteArray())
    }
    val dotenv = Dotenv.configure().directory(appDir.pathString).load()
    val apiKey = dotenv["OPENAI_API_KEY"]
    val apiProxy = dotenv["OPENAI_API_PROXY"] ?: null

    return OpenAIConnector(apiKey, OPENAI_MODEL[0], apiProxy)
}

