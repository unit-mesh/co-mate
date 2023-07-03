package org.archguard.comate.action

import org.archguard.comate.command.ComateContext
import org.archguard.comate.strategy.CodePromptStrategy
import org.archguard.comate.strategy.Strategy
import org.archguard.spec.lang.CaseFlowSpec

class DesignSystemPrompter(
    val context: ComateContext,
    override val strategy: Strategy,
) : CodePromptStrategy {
    override fun getRole(): String = "Senior Software architecture"
    override fun getInstruction(): String = "根据如下的 DSL，以及对应的需求信息，进行系统设计。"
    override fun getRequirements(): String = """
1. 按格式要求的 DSL 返回，不做解决，我们会根据 DSL 进行评估。

""".trimIndent()

    override fun getExtendData(): String {
        val dsl = CaseFlowSpec.defaultSpec().toString()
        return """
需求信息 as follow: 

###
${context.extArgs["actionInput"]}
###
    
            
Kotlin DSL as follow:
```kotlin
$dsl
```
            
        """.trimIndent()
    }
}