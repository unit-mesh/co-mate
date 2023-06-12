package org.archguard.meta.dsl.restful.rule

import org.archguard.meta.base.LlmRuleVerifier
import org.archguard.meta.base.LlmVerifyRule
import org.archguard.meta.base.RuleResult
import org.archguard.meta.model.RestApiElement

class MiscRule(private val ruleContent: String, override var ruleVerifier: LlmRuleVerifier) :
    LlmVerifyRule("security", ruleContent, ruleVerifier) {
    override fun exec(input: RestApiElement): RuleResult {
        return RuleResult(this.name, this.rule, ruleVerifier.check(ruleContent, input.toString()))
    }
}