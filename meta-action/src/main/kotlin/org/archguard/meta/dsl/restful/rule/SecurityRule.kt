package org.archguard.meta.dsl.restful.rule

import org.archguard.meta.base.LlmRuleVerifier
import org.archguard.meta.base.ApiLlmVerifyRule
import org.archguard.meta.base.RuleResult
import org.archguard.meta.model.RestApiElement

class SecurityRule(private val ruleContent: String, override var ruleVerifier: LlmRuleVerifier) :
    ApiLlmVerifyRule("security", ruleContent, ruleVerifier) {
    override fun exec(input: RestApiElement): RuleResult {
        return RuleResult(this.name, this.rule, ruleVerifier.check(ruleContent, input.toString()))
    }
}
