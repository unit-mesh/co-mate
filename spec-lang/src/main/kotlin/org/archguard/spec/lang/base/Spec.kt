package org.archguard.spec.lang.base

import org.archguard.spec.base.RuleResult
import org.archguard.spec.base.verifier.LlmRuleVerifier

interface Spec<T> {
    fun setVerifier(ruleVerifier: LlmRuleVerifier) {}
    fun exec(element: T): List<RuleResult> = listOf()
    fun default(): Spec<T>
}

