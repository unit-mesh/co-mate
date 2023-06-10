package org.archguard.meta.restful.rule

import org.archguard.meta.LlmRuleVerifier
import org.archguard.meta.restful.ApiLlmVerifyRule
import org.archguard.meta.restful.RestApi

class MiscRule(private val ruleContent: String, override var ruleVerifier: LlmRuleVerifier) :
    ApiLlmVerifyRule("security", ruleContent, ruleVerifier) {
    override fun exec(input: RestApi): Boolean {
        return ruleVerifier.check(ruleContent, input.toString())
    }
}