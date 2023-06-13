package org.archguard.meta.dsl.foundation.declaration

import org.archguard.meta.base.Rule
import org.archguard.meta.base.BaseDeclaration
import org.archguard.meta.dsl.DependencyRule
import org.archguard.meta.dsl.foundation.rule.LayeredDef
import org.archguard.meta.model.FoundationElement

class LayeredDeclaration : BaseDeclaration<FoundationElement> {
    private val dependencyRules = mutableListOf<DependencyRule>()
    private val layerRules = mutableListOf<LayeredDef>()

    fun layer(name: String, function: LayeredDef.() -> Unit): LayeredDef {
        val rule = LayeredDef(name)
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