package org.archguard.comate.action

import org.archguard.comate.code.FunctionCall
import org.archguard.comate.strategy.CodePromptStrategy
import org.archguard.comate.strategy.Strategy
import org.archguard.comate.wrapper.ComateSourceCodeContext
import org.archguard.scanner.analyser.KotlinAnalyser
import java.nio.file.Path

class LayeredStylePrompt(
    val context: CommandContext,
    override val strategy: Strategy,
) : CodePromptStrategy {
    override fun getRole(): String = "Architecture"
    override fun getInstruction(): String = "根据如下的调用信息，分析该业务场景的功能、分层架构，并使用 200 个字总结。"
    override fun getRequirements(): String = """
1. 使用中文描述。
2. 使用业务场景的语言描述，不要使用技术术语。

    """.trimIndent()

    override fun getExtendData(): String {
        val sourceCodeContext = ComateSourceCodeContext.create(context.workdir.toString(), context.lang)
        val codeDataStructs = KotlinAnalyser(sourceCodeContext).analyse()

        val funcName = "org.archguard.comate.cli.MainKt.main"
        val nodeTree = FunctionCall().analysis(funcName, codeDataStructs)

        val introduction = this.introduction(context.workdir)

        return """$introduction
function name: $funcName            
function calls tree: $nodeTree""".trimIndent()
    }
}