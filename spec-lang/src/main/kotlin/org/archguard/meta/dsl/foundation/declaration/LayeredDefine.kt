package org.archguard.meta.dsl.foundation.declaration

import org.archguard.meta.base.Rule
import org.archguard.meta.base.RuleResult
import org.archguard.meta.dsl.foundation.expression.NamingExpression
import org.archguard.meta.dsl.foundation.rule.NamingRule
import org.archguard.meta.model.FoundationElement

class LayeredDefine(val name: String) : Rule<FoundationElement> {
    var pattern: String? = null
    private var namingRule: NamingRule? = null
    private var filterPattern: Regex = Regex(".*")
    override val actionName: String get() = "LayeredRule: $pattern"

    fun pattern(pattern: String, block: NamingExpression) {
        this.pattern = pattern
        this.filterPattern = Regex(pattern)

        val namingRule = NamingRule()
        namingRule.delayBlock(block)
        this.namingRule = namingRule
    }

    fun naming(function: NamingExpression): NamingRule {
        val namingRule = NamingRule()
        namingRule.delayBlock(function)
        return namingRule
    }

    override fun exec(input: FoundationElement): List<RuleResult> {
        val results = mutableListOf<RuleResult>()
        input.ds
            .filter {
                filterPattern.matches(it.Package)
            }
            .map {
                namingRule!!.exec(it.NodeName)
            }
            .flatten().forEach {
                results.add(RuleResult("Layered for ${this.name}", this.actionName, it.result))
            }

        return results
    }
}