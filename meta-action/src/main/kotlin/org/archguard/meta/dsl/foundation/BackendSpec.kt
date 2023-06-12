package org.archguard.meta.dsl.foundation

import org.archguard.architecture.style.NamingStyle
import org.archguard.meta.base.AtomicRule

class NamingItem(val name: String = "") {
    fun style(style: String) {
        if (!NamingStyle.contains(style)) {
            throw IllegalArgumentException("Unknown naming style: $style. Supported styles: ${NamingStyle.valuesString()}")
        }
    }

    fun pattern(pattern: String, block: Naming.() -> Unit) {
        Naming().apply(block)
    }
}

class NamingDeclaration: BaseDeclaration {
    fun class_level(function: NamingItem.() -> Unit): NamingItem {
        val rule = NamingItem()
        rule.function()
        return rule
    }

    fun function_level(function: NamingItem.() -> Unit): NamingItem {
        val rule = NamingItem()
        rule.function()
        return rule
    }

    override fun rules(): List<AtomicRule> {
        return listOf()
    }
}