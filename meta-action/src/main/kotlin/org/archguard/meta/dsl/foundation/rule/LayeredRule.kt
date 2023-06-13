package org.archguard.meta.dsl.foundation.rule

import org.archguard.meta.base.AtomicAction
import org.archguard.meta.model.FoundationElement

class LayeredRule(val name: String) : AtomicAction<FoundationElement> {
    private var pattern: String? = null
    private var namingRuleRules: NamingRule? = null
    override val actionName: String get() = "LayeredRule: $pattern"

    fun pattern(pattern: String, block: NamingExpression) {
        this.pattern = pattern
        val namingRule = NamingRule()
        namingRule.delayBlock(block)
        this.namingRuleRules = namingRule
    }

    fun naming(function: NamingExpression): NamingRule {
        val namingRule = NamingRule()
        namingRule.delayBlock(function)
        return namingRule
    }
}