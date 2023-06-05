package org.archguard.comate.action

import org.archguard.comate.strategy.BasicPromptStrategy
import java.nio.file.Path

enum class ComateCommand(command: String) {
    None("") {
        override fun prompt(basepath: Path, lang: String): String = ""
    },
    Intro("intro") {
        override fun prompt(basepath: Path, lang: String): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = IntroductionCodePrompt(basepath, lang, promptStrategy)
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