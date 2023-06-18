package org.archguard.comate.action

import org.archguard.comate.command.ComateContext
import org.archguard.comate.strategy.CodePromptStrategy
import org.archguard.comate.strategy.Strategy

class LayeredStylePrompt(
    val context: ComateContext,
    override val strategy: Strategy,
) : CodePromptStrategy {
    override fun getRole(): String = "Software Architecture"
    override fun getInstruction(): String =
        "根据下面的信息，分析项目的分层是否符合业内的通用规范？并绘制 Graphviz 图来表示。"

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
        val introduction = context.fetchReadmeIntroduction()
        return """$introduction

package fan in: ${context.fetchPackageDependencies()}
"""
            .trimIndent()

    }

}

