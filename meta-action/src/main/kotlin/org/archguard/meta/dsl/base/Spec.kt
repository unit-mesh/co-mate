package org.archguard.meta.dsl.base

import org.archguard.meta.base.RuleResult
import org.archguard.meta.base.verifier.LlmRuleVerifier

interface Spec<T> {
    fun setVerifier(ruleVerifier: LlmRuleVerifier)
    fun exec(element: T): List<RuleResult>
}