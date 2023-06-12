package org.archguard.meta.dsl.restful

import org.archguard.meta.base.AtomicAction
import org.archguard.meta.base.AtomicDsl
import org.archguard.meta.base.LlmRuleVerifier

@AtomicDsl
abstract class AtomicRule(open val name: String, open var rule: String) : AtomicAction {
    abstract fun exec(input: RestApi): Any
}

abstract class ApiLlmVerifyRule(override val name: String, override var rule: String, open var ruleVerifier: LlmRuleVerifier) :
    AtomicRule(name, rule) {
    override fun exec(input: RestApi): Boolean {
        return exec(input)
    }
}