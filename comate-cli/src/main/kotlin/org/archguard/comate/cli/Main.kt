package org.archguard.comate.cli

import io.github.cdimascio.dotenv.Dotenv
import org.archguard.comate.command.ComateCommand
import org.archguard.comate.command.ComateContext
import org.archguard.comate.connector.OPENAI_MODEL
import org.archguard.comate.connector.OpenAIConnector
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
    val comateCommand = ComateCommand.fromText(cmd)

    logger.info("start execution ...")

    val openAiConnector = createConnector()
    val context = ComateContext(basePath, language, openAiConnector)

    val summarizePrompt = comateCommand.run(context)

    if (summarizePrompt.isEmpty()) {
        logger.warning("summarize prompt is empty")
        return
    }

    logger.info("summarize prompt text: $summarizePrompt")

    val output = openAiConnector.prompt(summarizePrompt)
    println(output)
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

