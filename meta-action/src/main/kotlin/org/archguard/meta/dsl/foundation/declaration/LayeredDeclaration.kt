package org.archguard.meta.dsl.foundation.declaration

import org.archguard.meta.base.AtomicAction
import org.archguard.meta.base.BaseDeclaration
import org.archguard.meta.dsl.DependencyRule
import org.archguard.meta.dsl.foundation.rule.LayeredRule
import org.archguard.meta.model.FoundationElement

class LayeredDeclaration : BaseDeclaration<FoundationElement> {
    private val dependencyRules = mutableListOf<DependencyRule>()
    private val layerRules = mutableListOf<LayeredRule>()

    fun layer(name: String, function: LayeredRule.() -> Unit): LayeredRule {
        val rule = LayeredRule(name)
        rule.function()
        layerRules.add(rule)
        return rule
    }

    fun dependency(function: DependencyRule.() -> Unit): DependencyRule {
        val rule = DependencyRule()
        rule.function()
        dependencyRules.add(rule)
        return rule
    }

    override fun rules(element: FoundationElement): List<AtomicAction<FoundationElement>> {
        return layerRules + dependencyRules
    }
}