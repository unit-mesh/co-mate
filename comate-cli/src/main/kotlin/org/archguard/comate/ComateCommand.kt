package org.archguard.comate

import org.archguard.comate.action.LayeredStylePrompt
import org.archguard.comate.action.IntroductionPrompt
import org.archguard.comate.strategy.BasicPromptStrategy
import java.nio.file.Path

enum class ComateCommand(command: String) {
    None("") {
        override fun prompt(basepath: Path, lang: String): String = ""
    },
    Intro("intro") {
        override fun prompt(basepath: Path, lang: String): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = IntroductionPrompt(basepath, lang, promptStrategy)
            return basicPrompter.prompt()
        }
    },
    LayeredStyle("archstyle") {
        override fun prompt(basepath: Path, lang: String): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = LayeredStylePrompt(basepath, lang, promptStrategy)
            return basicPrompter.prompt()
        }
    }
    ;

    abstract fun prompt(basepath: Path, lang: String): String
}