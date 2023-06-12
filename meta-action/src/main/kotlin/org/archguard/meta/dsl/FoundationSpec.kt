package org.archguard.meta.dsl

import org.archguard.meta.base.LlmRuleVerifier
import org.archguard.meta.base.RuleResult
import org.archguard.meta.base.Spec
import org.archguard.meta.base.SpecDsl
import org.archguard.meta.dsl.foundation.BaseDeclaration
import org.archguard.meta.dsl.foundation.LayeredDeclaration
import org.archguard.meta.dsl.foundation.NamingDeclaration
import org.archguard.meta.dsl.foundation.ProjectNameDecl
import org.archguard.meta.model.FoundationElement

class DependencyRule {
    val rules = mutableListOf<Pair<String, String>>()
    infix fun String.dependedOn(to: String) {
        rules.add(this to to)
    }
}

@SpecDsl
class FoundationSpec : Spec<FoundationElement> {
    val declarations = mutableListOf<BaseDeclaration>()

    fun project_name(function: ProjectNameDecl.() -> Unit): ProjectNameDecl {
        val rule = ProjectNameDecl()
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

    override fun context(ruleVerifier: LlmRuleVerifier) {

    }

    override fun exec(element: FoundationElement): Map<String, RuleResult> {
        declarations.map { declaration ->
            declaration.rules().map { rules ->
                rules.name to rules.exec(element)
            }
        }
    }
}

fun foundation(init: FoundationSpec.() -> Unit): FoundationSpec {
    val spec = FoundationSpec()
    spec.init()
    return spec
}
