package org.archguard.meta.dsl

import org.archguard.meta.base.LlmRuleVerifier
import org.archguard.meta.base.RuleResult
import org.archguard.meta.base.Spec
import org.archguard.meta.base.SpecDsl
import org.archguard.meta.dsl.domain.ContextMap

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
