package org.archguard.meta.dsl.restful.rule

import org.archguard.meta.base.LlmRuleVerifier
import org.archguard.meta.dsl.restful.ApiLlmVerifyRule
import org.archguard.meta.base.RuleResult
import org.archguard.meta.model.RestApiElement

class SecurityRule(private val ruleContent: String, override var ruleVerifier: LlmRuleVerifier) :
    ApiLlmVerifyRule("security", ruleContent, ruleVerifier) {
    override fun exec(input: RestApiElement): List<RuleResult> {
        return listOf(RuleResult(this.actionName, this.rule, ruleVerifier.check(ruleContent, input.toString())))
    }
}
