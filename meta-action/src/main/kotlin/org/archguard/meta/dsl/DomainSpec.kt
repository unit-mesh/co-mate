package org.archguard.meta.dsl

import org.archguard.meta.base.LlmRuleVerifier
import org.archguard.meta.base.RuleResult
import org.archguard.meta.base.Spec
import org.archguard.meta.base.SpecDsl
import org.archguard.meta.dsl.restful.RestApi

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
class DomainSpec: Spec {
    fun context_map(name: String, block: ContextMap.() -> Unit): ContextMap {
        val contextMap = ContextMap(name)
        contextMap.block()
        return contextMap
    }

    override fun context(ruleVerifier: LlmRuleVerifier) {

    }

    override fun exec(element: RestApi): Map<String, RuleResult> {
        return mapOf()
    }
}

fun domain(init: DomainSpec.() -> Unit): DomainSpec {
    val spec = DomainSpec()
    spec.init()
    return spec
}
