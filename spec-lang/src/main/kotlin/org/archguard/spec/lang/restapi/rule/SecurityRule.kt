package org.archguard.spec.lang.restapi.rule

import org.archguard.spec.base.verifier.LlmRuleVerifier
import org.archguard.spec.lang.restapi.ApiLlmVerifyRule
import org.archguard.spec.base.RuleResult
import org.archguard.spec.element.RestApiElement

class SecurityRule(private val ruleContent: String, override var ruleVerifier: LlmRuleVerifier) :
    ApiLlmVerifyRule("security", ruleContent, ruleVerifier) {
    override fun exec(input: RestApiElement): List<RuleResult> {
        return listOf(RuleResult(this.actionName, this.rule, ruleVerifier.check(ruleContent, input.toString())))
    }
}
