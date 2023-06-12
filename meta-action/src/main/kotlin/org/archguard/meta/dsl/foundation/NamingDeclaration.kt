package org.archguard.meta.dsl.foundation

import org.archguard.architecture.style.NamingStyle
import org.archguard.meta.base.AtomicAction
import org.archguard.meta.base.RuleResult
import org.archguard.meta.model.FoundationElement

class NamingItem(override val name: String = "namingItem") : AtomicAction<FoundationElement> {
    fun style(style: String) {
        if (!NamingStyle.contains(style)) {
            throw IllegalArgumentException("Unknown naming style: $style. Supported styles: ${NamingStyle.valuesString()}")
        }
    }

    fun pattern(pattern: String, block: Naming.() -> Unit) {
        Naming(pattern).apply(block)
    }

    override fun exec(input: FoundationElement): RuleResult {
        return RuleResult(name, "namingItem", true)
    }
}

class NamingDeclaration : BaseDeclaration<FoundationElement> {
    val rules: MutableList<AtomicAction<FoundationElement>> = mutableListOf()

    fun class_level(function: NamingItem.() -> Unit): NamingItem {
        val rule = NamingItem()
        rule.function()

        rules.add(rule)
        return rule
    }

    fun function_level(function: NamingItem.() -> Unit): NamingItem {
        val rule = NamingItem()
        rule.function()

        rules.add(rule)
        return rule
    }

    override fun rules(): List<AtomicAction<FoundationElement>> {
        return rules
    }

}