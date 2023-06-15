package org.archguard.comate.action

import org.archguard.comate.command.CommandContext
import org.archguard.comate.strategy.CodePromptStrategy
import org.archguard.comate.strategy.Strategy
import org.archguard.comate.wrapper.ComateSourceCodeContext

class LayeredStylePrompt(
    val context: CommandContext,
    override val strategy: Strategy,
) : CodePromptStrategy {
    override fun getRole(): String = "Software Architecture"
    override fun getInstruction(): String = "根据下面的信息，分析项目的分层是否符合业内的通用规范？并绘制 Graphviz 图来表示。"
    override fun getRequirements(): String = """
1. 如果存在相互引用，请用红线展示出来。
2. 只展示重要的分层，不要展示过多的细节。
4. 结合分层、subgraph 的方式来表示分层。
4. 示例如下：

```dot
digraph G {
    rankdir=TB;
    node [shape=record, fontname=Helvetica];
    edge [color=black, penwidth=1.0];
    subgraph cluster_{} {
        label="{} Layer"
    }
```
"""

    override fun getExtendData(): String {
        val codeContext = ComateSourceCodeContext.create(context)
        val codeDataStructs = codeAnalyser(context.language, codeContext)?.analyse() ?: emptyList()

        val packageList = codeDataStructs.map { it.Package }.distinct()
        val packageInOut = mutableMapOf<String, List<String>>()

        codeDataStructs.forEach {
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

//        println(UmlConverter().byPackage(codeDataStructs))

        // if value is empty, remove it
        val cleanPackageInOut = packageInOut.filter { it.value.isNotEmpty() }

        val introduction = this.introduction(context.workdir)
        return """$introduction

package fan in: $cleanPackageInOut
"""
            .trimIndent()

    }
}