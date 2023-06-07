package org.archguard.comate.action

import org.archguard.comate.code.FunctionCall
import org.archguard.comate.strategy.CodePromptStrategy
import org.archguard.comate.strategy.Strategy
import org.archguard.comate.wrapper.ComateSourceCodeContext
import org.archguard.scanner.analyser.KotlinAnalyser

class ApiGovernance(
    val context: CommandContext,
    override val strategy: Strategy,
) : CodePromptStrategy {
    override fun getRole(): String = "Architecture"
    override fun getInstruction(): String = "根据下面的信息，总结 API 的规范情况。"

    override fun getExtendData(): String {
        val sourceCodeContext = ComateSourceCodeContext.create(context.workdir.toString(), context.lang, features = listOf("apicalls", "datamap"))
        val codeDataStructs = KotlinAnalyser(sourceCodeContext).analyse()

        val funcName = ""
        val nodeTree = FunctionCall().analysis(funcName, codeDataStructs)

        val introduction = this.introduction(context.workdir)

        return """$introduction
function name: $funcName            
function calls tree: $nodeTree""".trimIndent()
    }
}