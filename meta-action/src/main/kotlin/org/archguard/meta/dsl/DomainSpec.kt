package org.archguard.meta.dsl

import org.archguard.meta.base.SpecDsl

class Context(name: String) {
    fun aggregate(name: String, function: () -> Unit) {

    }

    infix fun dependedOn(context: Context) {

    }

}

class ContextMap(name: String) {
    fun context(name: String, function: Context.() -> Unit): Context {
        val context = Context(name)
        context.function()
        return context
    }

    fun context(name: String): Context {
        return Context(name)
    }

    fun mapping(function: () -> Unit) {

    }

}

@SpecDsl
class DomainSpec {
    fun context_map(name: String, block: ContextMap.() -> Unit): ContextMap {
        val contextMap = ContextMap(name)
        contextMap.block()
        return contextMap
    }
}

fun domain(init: DomainSpec.() -> Unit): DomainSpec {
    val spec = DomainSpec()
    spec.init()
    return spec
}
