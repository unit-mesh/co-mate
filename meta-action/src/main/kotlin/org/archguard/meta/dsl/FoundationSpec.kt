package org.archguard.meta.dsl

import org.archguard.meta.base.LlmRuleVerifier
import org.archguard.meta.base.RuleResult
import org.archguard.meta.base.Spec
import org.archguard.meta.base.SpecDsl
import org.archguard.meta.base.BaseDeclaration
import org.archguard.meta.dsl.foundation.declaration.LayeredDeclaration
import org.archguard.meta.dsl.foundation.declaration.NamingDeclaration
import org.archguard.meta.dsl.foundation.declaration.ProjectNameDeclaration
import org.archguard.meta.model.FoundationElement

class DependencyRule {
    val rules = mutableListOf<Pair<String, String>>()
    infix fun String.dependedOn(to: String) {
        rules.add(this to to)
    }
}

@SpecDsl
class FoundationSpec : Spec<FoundationElement> {
    val declarations = mutableListOf<BaseDeclaration<FoundationElement>>()

    fun project_name(function: ProjectNameDeclaration.() -> Unit): ProjectNameDeclaration {
        val rule = ProjectNameDeclaration()
        rule.function()

        declarations.add(rule)
        return rule
    }

    fun layered(function: LayeredDeclaration.() -> Unit): LayeredDeclaration {
        val rule = LayeredDeclaration()
        rule.function()

        declarations.add(rule)
        return rule
    }


    fun naming(function: NamingDeclaration.() -> Unit): NamingDeclaration {
        val rule = NamingDeclaration()
        rule.function()

        declarations.add(rule)
        return rule
    }

    override fun setVerifier(ruleVerifier: LlmRuleVerifier) {

    }

    override fun exec(element: FoundationElement): List<RuleResult> {
        val rules = declarations.map { declaration -> declaration.rules(element) }.flatten()
        return rules.map { rule ->
            rule.exec(element) as List<RuleResult>
        }.flatten()
    }
}

fun foundation(init: FoundationSpec.() -> Unit): FoundationSpec {
    val spec = FoundationSpec()
    spec.init()
    return spec
}
