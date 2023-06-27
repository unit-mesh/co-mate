package org.archguard.comate.action

import org.archguard.comate.command.ComateContext
import org.archguard.comate.governance.ApiRuleVerifier
import org.archguard.comate.strategy.CodePromptStrategy
import org.archguard.comate.strategy.Strategy
import org.archguard.spec.element.FoundationElement
import org.archguard.spec.lang.FoundationSpec

class FoundationGovernancePrompter(
    val context: ComateContext,
    override val strategy: Strategy,
) : CodePromptStrategy {
    override fun getRole(): String = "Architecture"
    override fun getInstruction(): String = "根据下面的信息，总结项目的基础规范实施情况。"
    override fun getRequirements(): String = """
1. 使用中文描述。
2. 使用业务场景的语言描述，不要使用技术术语。
3. 如果 result 是 true，请不要返回任何信息。
4. 如果 result 是 false，请返回不通过的原因，并根据 rule 提供符合规范的 API。
5. 你只返回如下的结果类似于：

###
- `{xxx}` 不符合 { rule name } 规范，Rule: { rule }，建议修改为 {new api}。
###
""".trimIndent()

    override fun getExtendData(): String {
        val codeDataStructs = context.fetchDs()

        val governance = if (context.spec != null && context.spec!!.javaClass == FoundationSpec::class.java) {
            context.spec as FoundationSpec
        } else {
            FoundationSpec.defaultSpec()
        }

        governance.setVerifier(ApiRuleVerifier(context.connector!!))
        val ruleResults = governance.exec(FoundationElement(context.projectName, codeDataStructs))

        val failedResults = ruleResults.filter { !it.success }

        val introduction = context.fetchReadmeIntroduction()
        return """$introduction

failed spec results: $failedResults

""".trimIndent()
    }
}