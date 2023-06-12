package org.archguard.meta.dsl.restful.rule

import org.archguard.meta.base.LlmRuleVerifier
import org.archguard.meta.base.LlmVerifyRule
import org.archguard.meta.dsl.restful.RestApi

class MiscRule(private val ruleContent: String, override var ruleVerifier: LlmRuleVerifier) :
    LlmVerifyRule("security", ruleContent, ruleVerifier) {
    override fun exec(input: RestApi): Boolean {
        return ruleVerifier.check(ruleContent, input.toString())
    }
}