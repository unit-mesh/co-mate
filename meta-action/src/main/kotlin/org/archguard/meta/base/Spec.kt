package org.archguard.meta.base

interface Spec<T> {
    fun context(ruleVerifier: LlmRuleVerifier)
    fun exec(element: T): List<RuleResult>
}