package org.archguard.meta.dsl.foundation

import org.archguard.meta.base.AtomicAction
import org.archguard.meta.dsl.DependencyRule
import org.archguard.meta.model.FoundationElement

class LayeredDeclaration : BaseDeclaration<FoundationElement> {
    val dependencyRules = HashMap<String, List<String>>()

    fun layer(name: String, function: LayeredRule.() -> Unit): LayeredRule {
        val rule = LayeredRule()
        rule.function()
        return rule
    }

    fun dependency(function: DependencyRule.() -> Unit): DependencyRule {
        val rule = DependencyRule()
        rule.function()
        return rule
    }

    override fun rules(): List<AtomicAction<FoundationElement>> {
        return listOf()
    }

}