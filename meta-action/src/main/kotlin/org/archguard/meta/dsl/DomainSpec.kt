package org.archguard.meta.dsl

import org.archguard.meta.base.SpecDsl

class ContextMap {

}

@SpecDsl
class DomainSpec {
    fun context_map(block: ContextMap.() -> Unit): ContextMap {
        val contextMap = ContextMap()
        contextMap.block()
        return contextMap
    }
}

fun domain(init: DomainSpec.() -> Unit): DomainSpec {
    val spec = DomainSpec()
    spec.init()
    return spec
}
