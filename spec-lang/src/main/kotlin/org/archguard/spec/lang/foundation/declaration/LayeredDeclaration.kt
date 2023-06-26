package org.archguard.spec.lang.foundation.declaration

import org.archguard.spec.base.Rule
import org.archguard.spec.lang.base.BaseDeclaration
import org.archguard.spec.lang.foundation.rule.DependencyRule
import org.archguard.spec.element.FoundationElement
import org.jetbrains.annotations.TestOnly

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

//    override fun toString(): String {
//        var result = "";
//        result += layerRules.forEach {
//            """pattern("${it.pattern}") { name shouldBe endWiths("${it.name}") }"""
//        }
//
//        return result
//    }
}

@TestOnly
fun layered(function: LayeredDeclaration.() -> Unit): LayeredDeclaration {
    val declaration = LayeredDeclaration()
    declaration.function()
    return declaration
}