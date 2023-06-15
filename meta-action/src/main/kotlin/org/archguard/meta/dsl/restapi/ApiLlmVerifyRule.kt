package org.archguard.meta.dsl.restapi

import org.archguard.meta.base.LlmVerifierRule
import org.archguard.meta.base.verifier.LlmRuleVerifier
import org.archguard.meta.base.RuleResult
import org.archguard.meta.model.RestApiElement

abstract class ApiLlmVerifyRule(
    override val actionName: String,
    override var rule: String,
    open var ruleVerifier: LlmRuleVerifier,
) : LlmVerifierRule<RestApiElement>, ApiAtomicRule(actionName, rule) {
    override fun exec(input: RestApiElement): List<RuleResult> {
        TODO()
    }
}

