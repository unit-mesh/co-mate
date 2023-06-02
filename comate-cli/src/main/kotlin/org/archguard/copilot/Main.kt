package org.archguard.copilot

import org.archguard.comate.strategy.BasicPromptStrategy
import org.archguard.comate.action.IntroductionPrompt
import java.io.File
import kotlin.io.path.Path

fun main() {
    val basepath = Path(File(".").absolutePath)
    val promptStrategy = BasicPromptStrategy()
    val basicPrompter = IntroductionPrompt(basepath, promptStrategy)
    val prompt = basicPrompter.prompt()

    print(prompt)
}
