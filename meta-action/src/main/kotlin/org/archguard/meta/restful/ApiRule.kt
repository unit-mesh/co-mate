package org.archguard.meta.restful

import org.archguard.meta.AtomicAction
import org.archguard.meta.AtomicDsl
import org.archguard.meta.LlmRuleVerifier

@AtomicDsl
abstract class ApiRule(open val name: String, open var rule: String) : AtomicAction {
    abstract fun exec(input: RestApi): Any
}

abstract class ApiLlmVerifyRule(override val name: String, override var rule: String, open var ruleVerifier: LlmRuleVerifier) :
    ApiRule(name, rule) {
    override fun exec(input: RestApi): Boolean {
        return exec(input)
    }
}