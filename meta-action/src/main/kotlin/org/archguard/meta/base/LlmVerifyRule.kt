package org.archguard.meta.base

import org.archguard.meta.dsl.restful.RestApi

abstract class LlmVerifyRule(
    override val name: String,
    override var rule: String,
    open var ruleVerifier: LlmRuleVerifier,
) : ApiAtomicRule(name, rule) {
    override fun exec(input: RestApi): Boolean {
        return exec(input)
    }
}