package org.archguard.spec.lang.foundation.declaration

import org.archguard.spec.base.Rule
import org.archguard.spec.lang.base.BaseDeclaration
import org.archguard.spec.lang.foundation.rule.NamingStyleRule
import org.archguard.spec.element.FoundationElement
import org.jetbrains.annotations.TestOnly

enum class NamingTarget {
    Package,
    Class,
    Function,
}

class NamingDeclaration : BaseDeclaration<FoundationElement> {
    val rules: MutableList<Rule<FoundationElement>> = mutableListOf()

    fun class_level(function: NamingStyleRule.() -> Unit): NamingStyleRule {
        val rule = NamingStyleRule(NamingTarget.Class)
        rule.function()

        rules.add(rule)
        return rule
    }

    fun function_level(function: NamingStyleRule.() -> Unit): NamingStyleRule {
        val rule = NamingStyleRule(NamingTarget.Function)
        rule.function()

        rules.add(rule)
        return rule
    }

    override fun rules(element: FoundationElement): List<Rule<FoundationElement>> {
        return rules
    }

    override fun toString(): String {
        val rules = rules.joinToString(separator = "\n").lines().joinToString(separator = "\n") { "    $it" }
        return """naming {
$rules
}"""
    }
}

@TestOnly
fun naming_t(function: NamingDeclaration.() -> Unit): NamingDeclaration {
    val declaration = NamingDeclaration()
    declaration.function()
    return declaration
}