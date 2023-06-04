package org.archguard.comate

import io.github.cdimascio.dotenv.Dotenv
import org.archguard.comate.action.IntroductionPrompt
import org.archguard.comate.smart.OpenAIConnector
import org.archguard.comate.smart.Semantic
import org.archguard.comate.smart.cosineSimilarity
import org.archguard.comate.strategy.BasicPromptStrategy
import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path

typealias Embed = FloatArray

fun main(args: Array<String>) {
    val basepath = Path(File(".").absolutePath)
    val create = Semantic.create()

    val commandEmbedMap = createEmbedMap(create)

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

    when (comateCommand) {
        ComateCommand.None -> {
            println("不知道你在说什么")
        }
        else -> {
            println("prompt to openai...")
            val promptText = ComateCommand.Intro.prompt(basepath)
            val output = openAiConnector.prompt(promptText)
            println(output)
        }
    }
}

private fun createEmbedMap(create: Semantic): Map<ComateCommand, List<Embed>> {
    var commandEmbedMap: Map<ComateCommand, List<Embed>> = mapOf()
    val basicIntroCommand = listOf(
        "introduction system",
        "介绍一下这个系统",
        "介绍这个系统",
        "介绍系统",
    )
    val basicIntro = basicIntroCommand.map { create.embed(it) }

    commandEmbedMap = mapOf(
        ComateCommand.Intro to basicIntro,
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

enum class ComateCommand(command: String) {
    None("") {
        override fun prompt(basepath: Path): String = ""
    },
    Intro("intro") {
        override fun prompt(basepath: Path): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = IntroductionPrompt(basepath, promptStrategy)
            val prompt = basicPrompter.prompt()
            return prompt
        }
    };

    abstract fun prompt(basepath: Path): String
}