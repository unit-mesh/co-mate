package org.archguard.meta.dsl

import org.archguard.meta.base.LlmRuleVerifier
import org.archguard.meta.base.RuleResult
import org.archguard.meta.base.Spec
import org.archguard.meta.base.SpecDsl
import org.archguard.meta.dsl.domain.declaration.ContextMapDeclaration

@SpecDsl
class DomainSpec: Spec<Any> {
    fun context_map(name: String, block: ContextMapDeclaration.() -> Unit): ContextMapDeclaration {
        val contextMapDeclaration = ContextMapDeclaration(name)
        contextMapDeclaration.block()
        return contextMapDeclaration
    }

    override fun setVerifier(ruleVerifier: LlmRuleVerifier) {

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
