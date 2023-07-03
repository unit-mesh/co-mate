package org.archguard.comate.action

import org.archguard.comate.command.ComateContext
import org.archguard.comate.strategy.CodePromptStrategy
import org.archguard.comate.strategy.Strategy
import org.archguard.spec.lang.CaseFlowSpec

class DesignSystemPrompter(
    val context: ComateContext,
    override val strategy: Strategy,
) : CodePromptStrategy {
    override fun getRole(): String = "Architecture"
    override fun getInstruction(): String = "根据详细分析如下的需求信息，设计完整的端到端需求用例。"
    override fun getRequirements(): String = """
1. 请按如下的 DSL 格式返回，不做解释。

""".trimIndent()

    override fun getExtendData(): String {
        val dsl = CaseFlowSpec.exampleActivitySpec().toString()
        return """
需求信息 如下: 

###
${context.extArgs["actionInput"]}
###
    
            
DSL 格式如下:
```kotlin
$dsl
```
            
        """.trimIndent()
    }
}