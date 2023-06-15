package org.archguard.meta.dsl.foundation.declaration

import org.archguard.meta.base.Rule
import org.archguard.meta.dsl.base.BaseDeclaration
import org.archguard.meta.dsl.foundation.rule.DependencyRule
import org.archguard.meta.model.FoundationElement

class LayeredDeclaration : BaseDeclaration<FoundationElement> {
    private val dependencyRules = mutableListOf<DependencyRule>()
    private val layerRules = mutableListOf<LayeredDefine>()

    fun layer(name: String, function: LayeredDefine.() -> Unit): LayeredDefine {
        val rule = LayeredDefine(name)
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

    override fun rules(element: FoundationElement): List<Rule<FoundationElement>> {
        return layerRules + dependencyRules
    }
}