package org.archguard.comate.action

import org.archguard.comate.code.FunctionCall
import org.archguard.comate.strategy.CodePromptStrategy
import org.archguard.comate.strategy.Strategy
import org.archguard.comate.wrapper.ComateSourceCodeContext

class LayeredStylePrompt(
    val context: CommandContext,
    override val strategy: Strategy,
) : CodePromptStrategy {
    override fun getRole(): String = "Architecture"
    override fun getInstruction(): String = "根据下面的信息，总结项目的基础规范实施情况。"
    override fun getRequirements(): String = """
1. 使用中文描述。
2. 使用业务场景的语言描述，不要使用技术术语。

    """.trimIndent()

    override fun getExtendData(): String {
        val codeContext = ComateSourceCodeContext.create(context.workdir.toString(), context.lang)
        val codeDataStructs = codeAnalyser(context.lang, codeContext)?.analyse()

        val funcName = context.extArgs["funcName"] ?: throw Exception("funcName is required")

        val nodeTree = if (codeDataStructs != null) {
            FunctionCall().analysis(funcName, codeDataStructs)
        } else {
            null
        }

        val introduction = this.introduction(context.workdir)

        return """$introduction
function name: $funcName            
function calls tree: $nodeTree""".trimIndent()
    }
}