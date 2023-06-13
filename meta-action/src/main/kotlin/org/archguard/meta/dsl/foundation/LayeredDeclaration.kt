package org.archguard.meta.dsl.foundation

import org.archguard.meta.base.AtomicAction
import org.archguard.meta.dsl.DependencyRule
import org.archguard.meta.model.FoundationElement

class LayeredDeclaration : BaseDeclaration<FoundationElement> {
    private val dependencyRules = mutableListOf<Pair<String, String>>()

    fun layer(name: String, function: LayeredRule.() -> Unit): LayeredRule {
        val rule = LayeredRule(name)
        rule.function()
        return rule
    }

    fun dependency(function: DependencyRule.() -> Unit): DependencyRule {
        val rule = DependencyRule()
        rule.function()
        dependencyRules.addAll(rule.rules)
        return rule
    }

    override fun rules(): List<AtomicAction<FoundationElement>> {
        return listOf()
    }

}