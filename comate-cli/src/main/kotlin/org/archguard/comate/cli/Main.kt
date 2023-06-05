package org.archguard.comate.cli

import io.github.cdimascio.dotenv.Dotenv
import org.archguard.comate.action.ComateCommand
import org.archguard.comate.smart.OpenAIConnector
import org.archguard.comate.smart.Semantic
import org.archguard.comate.smart.cosineSimilarity
import java.io.File
import java.util.logging.Logger
import kotlin.io.path.Path

typealias Embed = FloatArray

private val logger = Logger.getLogger("comate")

fun main(args: Array<String>) {
    val basePath = Path(File(".").absolutePath)
    val semantic = Semantic.create()

    val commandEmbedMap = createEmbedMap(semantic)

    // layered style
    val cmd = if (args.isEmpty()) "layered style" else args[0]

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

    logger.info("prompt to openai...")

    val promptText = when (comateCommand) {
        ComateCommand.Intro -> {
             ComateCommand.Intro.prompt(basePath, "kotlin")
        }

        ComateCommand.LayeredStyle -> {
            ComateCommand.LayeredStyle.prompt(basePath, "kotlin")
        }
        ComateCommand.None -> null
    } ?: return

    logger.info("prompt text: $promptText")

    val openAiConnector = createConnector()
    val output = openAiConnector.prompt(promptText)
    println(output)
}

private fun createEmbedMap(create: Semantic): Map<ComateCommand, List<Embed>> {
    var commandEmbedMap: Map<ComateCommand, List<Embed>> = mapOf()
    val basicIntroCommand = listOf(
        "introduction system",
        "介绍一下这个系统",
        "介绍这个系统",
        "介绍系统",
    )
    val archStyleCommand = listOf(
        "layered style",
        "what is layered style",
        "系统的分层",
    );

    commandEmbedMap = mapOf(
        ComateCommand.Intro to basicIntroCommand.map { create.embed(it) },
        ComateCommand.LayeredStyle to archStyleCommand.map { create.embed(it) },
    )
    return commandEmbedMap
}

private fun createConnector(): OpenAIConnector {
    val appDir = File(System.getProperty("user.home"), ".comate")
    val dotenv = Dotenv.configure().directory(appDir.toString()).load()
    val apiKey = dotenv["OPENAI_API_KEY"]
    return OpenAIConnector(apiKey)
}

