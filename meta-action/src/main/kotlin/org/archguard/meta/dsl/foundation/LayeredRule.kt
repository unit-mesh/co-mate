package org.archguard.meta.dsl.foundation

import org.archguard.meta.base.AtomicAction
import org.archguard.meta.model.FoundationElement

class LayeredRule : AtomicAction<FoundationElement> {
    private var pattern: String? = null
    private var namingRules: Naming? = null
    override val actionName: String get() = "LayeredRule: $pattern"

    fun pattern(pattern: String, block: NamingRule) {
        this.pattern = pattern
        val naming = Naming()
        naming.delayBlock(block)
        this.namingRules = naming
    }

    fun naming(function: NamingRule): Naming {
        val naming = Naming()
        naming.delayBlock(function)
        return naming
    }
}