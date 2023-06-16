package org.archguard.comate.action

import org.archguard.comate.command.ComateWorkspace
import org.archguard.comate.strategy.CodePromptStrategy
import org.archguard.comate.strategy.Strategy
import org.archguard.comate.wrapper.ComateSourceCodeContext
import org.archguard.spec.lang.foundation

class FoundationGovernancePrompter(
    val context: ComateWorkspace,
    override val strategy: Strategy,
) : CodePromptStrategy {
    override fun getRole(): String = "Architecture"
    override fun getInstruction(): String = "根据下面的信息，总结项目的基础规范实施情况。"
    override fun getRequirements(): String = """
1. 使用中文描述。
2. 使用业务场景的语言描述，不要使用技术术语。

    """.trimIndent()

    override fun getExtendData(): String {
        val codeContext = ComateSourceCodeContext.create(context)
        val codeDataStructs = codeAnalyser(context.language, codeContext)?.analyse()

        val governance = foundation {

        }

//        governance.exec(codeDataStructs)

        val introduction = this.introduction(context.workdir)
        return """$introduction

""".trimIndent()
    }
}