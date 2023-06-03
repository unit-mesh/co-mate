package org.archguard.comate

import org.archguard.comate.action.IntroductionPrompt
import org.archguard.comate.strategy.BasicPromptStrategy
import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path

fun main(args: Array<String>) {
    val basepath = Path(File(".").absolutePath)

    val text = ComateCommand.Intro.prompt(basepath)

    println(text)
}

enum class ComateCommand {
    Intro {
        override fun prompt(basepath: Path): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = IntroductionPrompt(basepath, promptStrategy)
            val prompt = basicPrompter.prompt()
            return prompt
        }
    };

    abstract fun prompt(basepath: Path): String
}