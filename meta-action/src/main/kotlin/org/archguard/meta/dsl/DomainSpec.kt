package org.archguard.meta.dsl

import org.archguard.meta.base.LlmRuleVerifier
import org.archguard.meta.base.RuleResult
import org.archguard.meta.base.Spec
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
class DomainSpec: Spec<Any> {
    fun context_map(name: String, block: ContextMap.() -> Unit): ContextMap {
        val contextMap = ContextMap(name)
        contextMap.block()
        return contextMap
    }

    override fun context(ruleVerifier: LlmRuleVerifier) {

    }

    override fun exec(element: Any): List<RuleResult> {
        return listOf()
    }
}

fun domain(init: DomainSpec.() -> Unit): DomainSpec {
    val spec = DomainSpec()
    spec.init()
    return spec
}
