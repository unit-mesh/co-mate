package org.archguard.meta.base

interface Spec<T> {
    fun setVerifier(ruleVerifier: LlmRuleVerifier)
    fun exec(element: T): List<RuleResult>
}