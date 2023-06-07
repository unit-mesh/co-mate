package org.archguard.meta.restful.rule

import org.archguard.meta.LlmRuleVerifier
import org.archguard.meta.restful.ApiRule
import org.archguard.meta.restful.RestApi

class SecurityRule(private val ruleContent: String, private val ruleVerifier: LlmRuleVerifier) : ApiRule("security") {
    override fun exec(input: RestApi): Boolean {
        return ruleVerifier.check(ruleContent, input.toString())
    }
}
