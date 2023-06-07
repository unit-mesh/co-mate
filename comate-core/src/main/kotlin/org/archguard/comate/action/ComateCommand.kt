package org.archguard.comate.action

import org.archguard.comate.strategy.BasicPromptStrategy
import java.nio.file.Path

data class CommandContext(val workdir: Path, val lang: String)

enum class ComateCommand(command: String) {
    None("") {
        override fun run(context: CommandContext): String = ""
    },
    Intro("intro") {
        override fun run(context: CommandContext): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = IntroductionCodePrompt(context, promptStrategy)
            return basicPrompter.prompt()
        }
    },
    LayeredStyle("archstyle") {
        override fun run(context: CommandContext): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = LayeredStylePrompt(context, promptStrategy)
            return basicPrompter.prompt()
        }
    },
    ApiGovernance("api-gov") {
        override fun run(context: CommandContext): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = ApiGovernancePrompt(context, promptStrategy)
            return basicPrompter.prompt()
        }
    },
    ;

    abstract fun run(context: CommandContext): String
}