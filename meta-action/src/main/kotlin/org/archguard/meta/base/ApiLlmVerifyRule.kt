package org.archguard.meta.base

import org.archguard.meta.model.RestApiElement

abstract class ApiLlmVerifyRule(
    override val name: String,
    override var rule: String,
    open var ruleVerifier: LlmRuleVerifier,
) : BaseLlmVerifier<RestApiElement>, ApiAtomicRule(name, rule) {
    override fun exec(input: RestApiElement): List<RuleResult> {
        TODO()
    }
}

