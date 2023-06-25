package org.archguard.spec.lang.foundation.declaration

import org.archguard.spec.base.Rule
import org.archguard.spec.base.RuleResult
import org.archguard.spec.lang.foundation.expression.NamingExpression
import org.archguard.spec.lang.foundation.rule.NamingRule
import org.archguard.spec.element.FoundationElement

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
                results.add(RuleResult("Layered for ${this.name}", this.actionName, it.success, it.originValue))
            }

        return results
    }
}