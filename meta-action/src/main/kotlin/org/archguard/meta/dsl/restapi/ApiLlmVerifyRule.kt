package org.archguard.meta.dsl.restapi

import org.archguard.meta.base.BaseLlmVerifier
import org.archguard.meta.base.LlmRuleVerifier
import org.archguard.meta.base.RuleResult
import org.archguard.meta.model.RestApiElement

abstract class ApiLlmVerifyRule(
    override val actionName: String,
    override var rule: String,
    open var ruleVerifier: LlmRuleVerifier,
) : BaseLlmVerifier<RestApiElement>, ApiAtomicRule(actionName, rule) {
    override fun exec(input: RestApiElement): List<RuleResult> {
        TODO()
    }
}

