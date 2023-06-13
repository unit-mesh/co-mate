package org.archguard.meta.dsl.foundation

import org.archguard.meta.base.AtomicAction
import org.archguard.meta.model.FoundationElement

class LayeredRule : AtomicAction<FoundationElement> {
    private var pattern: String? = null
    private var namingRules: Naming? = null
    override val name: String get() = "LayeredRule: $pattern"

    fun pattern(pattern: String, block: Naming.() -> Unit) {
        this.pattern = pattern
        this.namingRules = Naming(block)
    }

    fun naming(function: Naming.() -> Unit): Naming {
        return Naming(function)
    }
}