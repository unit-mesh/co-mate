package org.archguard.spec.lang.restapi

import org.archguard.spec.base.LlmRule
import org.archguard.spec.base.verifier.LlmRuleVerifier
import org.archguard.spec.base.RuleResult
import org.archguard.spec.element.RestApiElement

abstract class ApiLlmVerifyRule(
    override val actionName: String,
    override var rule: String,
    open var ruleVerifier: LlmRuleVerifier,
) : LlmRule<RestApiElement>, ApiAtomicRule(actionName, rule) {
    override fun exec(input: RestApiElement): List<RuleResult> {
        TODO()
    }
}

