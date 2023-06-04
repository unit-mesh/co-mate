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


fun main(args: Array<String>) {
    val basepath = Path(File(".").absolutePath)
    val create = Semantic.create()
    val basicIntroCommand = listOf(
        "introduction system",
        "介绍一下这个系统",
        "介绍这个系统",
        "介绍系统",
    )

    val basicIntro = basicIntroCommand.map { create.embed(it) }

    val cmd = if (args.isEmpty()) {
        "introduction systems"
    } else {
        args[0]
    }

    val input = create.embed(cmd)
    var isMatchIntro = false

    run breaking@{
        basicIntro.forEach {
            try {
                val similarity = cosineSimilarity(it, input)
                if (similarity > 0.6) {
                    isMatchIntro = true
                    return@breaking
                }
            } catch (e: Exception) {
//                println(e)
            }
        }
    }

    val appDir = File("${System.getProperty("user.home")}", ".comate")
    val dotenv = Dotenv.configure().directory(appDir.toString()).load()
    val apiKey = dotenv["OPENAI_API_KEY"]

    val openAiConnector = OpenAIConnector(apiKey)

    if (isMatchIntro) {
        val promptStrategy = BasicPromptStrategy()
        val basicPrompter = IntroductionPrompt(basepath, promptStrategy)
        val prompt = basicPrompter.prompt()
        println("prompt to openai...")
        val output = openAiConnector.prompt(prompt)
        println(output)
    } else {
        println("不知道你在说什么")
    }
}

enum class ComateCommand(command: String) {
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