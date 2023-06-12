package org.archguard.meta.base

import org.archguard.meta.model.RestApiElement

abstract class LlmVerifyRule(
    override val name: String,
    override var rule: String,
    open var ruleVerifier: LlmRuleVerifier,
) : AtomicRule(name, rule) {
    override fun exec(input: RestApiElement): Boolean {
        return exec(input)
    }
}