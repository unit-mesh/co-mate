package org.archguard.meta.dsl.foundation

class LayeredRule {
    private var pattern: String? = null
    private var namingRules: Naming? = null

    fun pattern(pattern: String, block: Naming.() -> Unit) {
        this.pattern = pattern
        this.namingRules = Naming(pattern).apply(block)
    }

    fun naming(function: Naming.() -> Unit): Naming {
        val rule = Naming(pattern!!)
        rule.function()
        return rule
    }
}