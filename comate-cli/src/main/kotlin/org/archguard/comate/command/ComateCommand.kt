package org.archguard.comate.command

import org.archguard.comate.action.*
import org.archguard.comate.action.IntroductionCodePrompt
import org.archguard.comate.smart.Semantic
import org.archguard.comate.smart.cosineSimilarity
import org.archguard.comate.strategy.BasicPromptStrategy

enum class ComateCommand(val command: String) {
    None("") {
        override fun run(context: ComateContext): String = ""
    },
    Intro("intro") {
        override fun run(context: ComateContext): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = IntroductionCodePrompt(context, promptStrategy)
            return basicPrompter.execute()
        }
    },
    LayeredStyle("archstyle") {
        override fun run(context: ComateContext): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = LayeredStylePrompt(context, promptStrategy)
            return basicPrompter.execute()
        }
    },
    ApiGovernance("api-gov") {
        override fun run(context: ComateContext): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = ApiGovernancePrompter(context, promptStrategy)
            return basicPrompter.execute()
        }
    },
    FoundationGovernance("foundation-gov") {
        override fun run(context: ComateContext): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = FoundationGovernancePrompter(context, promptStrategy)
            return basicPrompter.execute()
        }
    },
    ApiGen("api-gen") {
        override fun run(context: ComateContext): String {
            val promptStrategy = BasicPromptStrategy()
            val basicPrompter = ApiGenPrompter(context, promptStrategy)
            return basicPrompter.execute()
        }
    }
    ;

    abstract fun run(context: ComateContext): String

    companion object {
        fun fromText(cmd: String): ComateCommand {
            val semantic = Semantic.create()
            val commandEmbedMap = createFunctionCallingEmbedding(semantic)

            val inputEmbed = semantic.embed(cmd)

            var comateCommand = None
            run breaking@{
                commandEmbedMap.forEach { (command, embeds) ->
                    embeds.forEach {
                        try {
                            val similarity = cosineSimilarity(it, inputEmbed)
                            // todo: 1. make this threshold configurable a
                            // todo: 2. choose the command with highest similarity
                            if (similarity > 0.6) {
                                comateCommand = command
                                return@breaking
                            }
                        } catch (e: Exception) {
//                println(e)
                        }
                    }
                }
            }

            return comateCommand
        }
    }
}