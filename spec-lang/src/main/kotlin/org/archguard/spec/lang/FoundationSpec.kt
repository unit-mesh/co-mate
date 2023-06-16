package org.archguard.spec.lang

import org.archguard.spec.base.*
import org.archguard.spec.base.verifier.LlmRuleVerifier
import org.archguard.spec.lang.base.BaseDeclaration
import org.archguard.spec.lang.base.Spec
import org.archguard.spec.lang.foundation.declaration.LayeredDeclaration
import org.archguard.spec.lang.foundation.declaration.NamingDeclaration
import org.archguard.spec.lang.foundation.rule.ProjectNameRule
import org.archguard.spec.lang.foundation.declaration.LayeredDefine
import org.archguard.spec.element.FoundationElement

@SpecDsl
class FoundationSpec : Spec<FoundationElement> {
    private val declarations = mutableListOf<BaseDeclaration<FoundationElement>>()

    fun project_name(function: ProjectNameRule.() -> Unit): ProjectNameRule {
        val rule = ProjectNameRule()
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
        val rules = declarations.map { declaration ->
            declaration.rules(element)
        }.flatten()

        // update context
        element.layeredDefines = rules.filterIsInstance<LayeredDefine>()

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
