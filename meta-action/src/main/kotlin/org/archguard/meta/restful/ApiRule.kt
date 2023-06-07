package org.archguard.meta.restful

import org.archguard.meta.AtomicAction
import org.archguard.meta.AtomicDsl
import org.archguard.meta.LlmRuleVerifier

@AtomicDsl
abstract class ApiRule(val name: String) : AtomicAction {
    abstract fun exec(input: RestApi): Any
}

abstract class ApiCheckRule(name: String, open var ruleVerifier: LlmRuleVerifier) : ApiRule(name) {
    override fun exec(input: RestApi): Boolean {
        return exec(input)
    }
}