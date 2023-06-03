package org.archguard.comate

import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.completion.chat.ChatMessageRole
import com.theokanning.openai.service.OpenAiService
import io.github.cdimascio.dotenv.Dotenv
import org.archguard.comate.action.IntroductionPrompt
import org.archguard.comate.smart.Semantic
import org.archguard.comate.smart.cosineSimilarity
import org.archguard.comate.strategy.BasicPromptStrategy
import java.io.File
import java.nio.file.Path
import java.time.Duration
import kotlin.io.path.Path


fun main(args: Array<String>) {
    val basepath = Path(File(".").absolutePath)
    val create = Semantic.create()
    val basicIntroCommand = listOf(
        "introduction system",
        "介绍一下这个系统",
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

    val timeout = Duration.ofSeconds(600)
    val service = OpenAiService(apiKey, timeout)

    if (isMatchIntro) {
        val promptStrategy = BasicPromptStrategy()
        val basicPrompter = IntroductionPrompt(basepath, promptStrategy)
        val prompt = basicPrompter.prompt()
        println("prompt to openai: $prompt")
        val output = prompt(service, prompt)
        println(output)
    } else {
        println("不知道你在说什么")
    }
}

fun prompt(service: OpenAiService, promptText: String): String {
    val messages: MutableList<ChatMessage> = ArrayList()
    val systemMessage = ChatMessage(ChatMessageRole.USER.value(), promptText)

    messages.add(systemMessage)
    val completionRequest = ChatCompletionRequest.builder()
        .model("gpt-3.5-turbo")
        .temperature(0.0)
        .messages(messages)
        .build()

    val completion = service.createChatCompletion(completionRequest)
    val output = completion
        .choices[0].message.content

    return output
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