package org.archguard.comate.action

import org.archguard.comate.strategy.BasicPromptStrategy
import java.nio.file.Path

data class CommandContext(val basepath: Path, val lang: String)

enum class ComateCommand(command: String) {
    None("") {
        override fun run(basepath: Path, lang: String): String = ""
    },
    Intro("intro") {
        override fun run(basepath: Path, lang: String): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = IntroductionCodePrompt(basepath, lang, promptStrategy)
            return basicPrompter.prompt()
        }
    },
    LayeredStyle("archstyle") {
        override fun run(basepath: Path, lang: String): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = LayeredStylePrompt(basepath, lang, promptStrategy)
            return basicPrompter.prompt()
        }
    },
    ApiGovernance("api-gov") {
        override fun run(basepath: Path, lang: String): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = ApiGovernancePrompt(basepath, lang, promptStrategy)
            return basicPrompter.prompt()
        }
    },
    ;

    abstract fun run(basepath: Path, lang: String): String
}