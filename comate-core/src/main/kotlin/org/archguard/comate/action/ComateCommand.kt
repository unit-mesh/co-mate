package org.archguard.comate.action

import org.archguard.comate.smart.OpenAIConnector
import org.archguard.comate.strategy.BasicPromptStrategy
import java.nio.file.Path

data class CommandContext(val workdir: Path, val lang: String, val connector: OpenAIConnector?)

enum class ComateCommand(command: String) {
    None("") {
        override fun run(context: CommandContext): String = ""
    },
    Intro("intro") {
        override fun run(context: CommandContext): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = IntroductionCodePrompt(context, promptStrategy)
            return basicPrompter.execute()
        }
    },
    LayeredStyle("archstyle") {
        override fun run(context: CommandContext): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = LayeredStylePrompt(context, promptStrategy)
            return basicPrompter.execute()
        }
    },
    ApiGovernance("api-gov") {
        override fun run(context: CommandContext): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = ApiGovernancePrompter(context, promptStrategy)
            return basicPrompter.execute()
        }
    },
    ;

    abstract fun run(context: CommandContext): String
}