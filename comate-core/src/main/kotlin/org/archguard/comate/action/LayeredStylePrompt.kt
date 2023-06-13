package org.archguard.comate.action

import org.archguard.comate.strategy.CodePromptStrategy
import org.archguard.comate.strategy.Strategy
import org.archguard.comate.wrapper.ComateSourceCodeContext

class LayeredStylePrompt(
    val context: CommandContext,
    override val strategy: Strategy,
) : CodePromptStrategy {
    override fun getRole(): String = "Architecture"
    override fun getInstruction(): String = "根据下面的信息，分析项目的分层是否符合业内的通用规范？并绘制 PlantUML 图来表示。"

    override fun getExtendData(): String {
        val codeContext = ComateSourceCodeContext.create(context.workdir.toString(), context.lang)
        val codeDataStructs = codeAnalyser(context.lang, codeContext)?.analyse()

        val packageList = codeDataStructs?.map { it.Package }?.distinct() ?: emptyList()
        val packageInOut = mutableMapOf<String, List<String>>()

        codeDataStructs?.forEach {
            val packageIn = it.Package
            if (packageIn.isEmpty()) return@forEach

            packageInOut[packageIn] = packageInOut[packageIn].orEmpty().toMutableList().apply {
                val elements = it.Imports.map { import -> import.Source }
                    .map { source -> source.substringBeforeLast(".") }
                    .filter { source -> packageList.contains(source) }

                elements.forEach { element ->
                    if (!this.contains(element)) {
                        this.add(element)
                    }
                }
            }
        }

        val introduction = this.introduction(context.workdir)
        return """$introduction

package fan in: $packageInOut
"""
            .trimIndent()

    }
}