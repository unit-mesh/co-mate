package org.archguard.comate

import io.github.cdimascio.dotenv.Dotenv
import org.archguard.comate.smart.OpenAIConnector
import org.archguard.comate.smart.Semantic
import org.archguard.comate.smart.cosineSimilarity
import java.io.File
import java.util.logging.Logger
import kotlin.io.path.Path

typealias Embed = FloatArray

private val logger = Logger.getLogger("comate")

fun main(args: Array<String>) {
    val basepath = Path(File(".").absolutePath)
    val create = Semantic.create()

    val commandEmbedMap = createEmbedMap(create)

    // architecture style
    val cmd = if (args.isEmpty()) "introduction systems" else args[0]

    val inputEmbed = create.embed(cmd)

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

    val openAiConnector = createConnector()

    if (comateCommand == ComateCommand.None) {
        println("no command found")
        return;
    }

    logger.info("prompt to openai...")

    val output = when (comateCommand) {
        ComateCommand.Intro -> {
            val promptText = ComateCommand.Intro.prompt(basepath, "kotlin")
            logger.info("prompt text: $promptText")
            openAiConnector.prompt(promptText)
        }

        ComateCommand.ArchStyle -> {
            val promptText = ComateCommand.ArchStyle.prompt(basepath, "kotlin")
            logger.info("prompt text: $promptText")
            openAiConnector.prompt(promptText)
        }

        else -> ""
    }

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
        "architecture style",
        "what is system architecture style",
        "系统的架构风格是什么？",
        "架构风格是啥",
        "架构风格是啥？",
    );

    commandEmbedMap = mapOf(
        ComateCommand.Intro to basicIntroCommand.map { create.embed(it) },
        ComateCommand.ArchStyle to archStyleCommand.map { create.embed(it) },
    )
    return commandEmbedMap
}

private fun createConnector(): OpenAIConnector {
    val appDir = File("${System.getProperty("user.home")}", ".comate")
    val dotenv = Dotenv.configure().directory(appDir.toString()).load()
    val apiKey = dotenv["OPENAI_API_KEY"]
    val openAiConnector = OpenAIConnector(apiKey)
    return openAiConnector
}

