package org.archguard.meta.dsl.domain

class Mapping {}

class ContextMap(name: String) {
    fun context(name: String, function: Context.() -> Unit): Context {
        val context = Context(name)
        context.function()
        return context
    }

    fun context(name: String): Context {
        return Context(name)
    }

    fun mapping(function: () -> Unit): Mapping {
        val mapping = Mapping()
        function()
        return mapping
    }
}