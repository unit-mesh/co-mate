package org.archguard.comate.action

import org.archguard.comate.strategy.CodePromptStrategy
import org.archguard.comate.strategy.Strategy
import org.archguard.comate.wrapper.ComateArchGuardClient
import org.archguard.comate.wrapper.ComateSourceCodeContext
import org.slf4j.LoggerFactory

class ApiGovernance(
    val context: CommandContext,
    override val strategy: Strategy,
) : CodePromptStrategy {
    override fun getRole(): String = "Architecture"
    override fun getInstruction(): String = "根据下面的信息，总结 API 的规范情况。"

    override fun getExtendData(): String {
        val client = ComateArchGuardClient()

        logger.info("start to analyse code: $context")
        val codeContext = ComateSourceCodeContext.custom(
            client,
            context.workdir.toString(),
            context.lang,
            features = listOf()
        )

        codeAnalyser(context.lang, codeContext)?.analyse()
        val apis = client.apis
        println(apis)

        logger.info("finished analyse code: ${context.workdir}")

        val introduction = this.introduction(context.workdir)

        throw NotImplementedError()

        return """$introduction

""".trimIndent()
    }

    companion object {
        val logger = LoggerFactory.getLogger(ApiGovernance::class.java)
    }
}