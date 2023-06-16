package org.archguard.comate.command

import org.archguard.comate.action.*
import org.archguard.comate.code.IntroductionCodePrompt
import org.archguard.comate.strategy.BasicPromptStrategy

enum class ComateCommand(val command: String) {
    None("") {
        override fun run(context: ComateWorkspace): String = ""
    },
    Intro("intro") {
        override fun run(context: ComateWorkspace): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = IntroductionCodePrompt(context, promptStrategy)
            return basicPrompter.execute()
        }
    },
    LayeredStyle("archstyle") {
        override fun run(context: ComateWorkspace): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = LayeredStylePrompt(context, promptStrategy)
            return basicPrompter.execute()
        }
    },
    ApiGovernance("api-gov") {
        override fun run(context: ComateWorkspace): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = ApiGovernancePrompter(context, promptStrategy)
            return basicPrompter.execute()
        }
    },
    FoundationGovernance("foundation-gov") {
        override fun run(context: ComateWorkspace): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = FoundationGovernancePrompter(context, promptStrategy)
            return basicPrompter.execute()
        }
    },
    ApiGen("api-gen") {
        override fun run(context: ComateWorkspace): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = ApiGenPrompter(context, promptStrategy)
            return basicPrompter.execute()
        }
    }
    ;

    abstract fun run(context: ComateWorkspace): String
}