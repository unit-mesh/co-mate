package org.archguard.spec.lang.base

import org.archguard.spec.base.RuleResult
import org.archguard.spec.base.verifier.LlmRuleVerifier

interface Spec<T> {
    fun setVerifier(ruleVerifier: LlmRuleVerifier) {}
    fun exec(element: T): List<Any> = listOf()
    fun default(): Spec<T>
    fun example(): String = ""
}

interface RuleSpec<T> : Spec<T> {
    override fun setVerifier(ruleVerifier: LlmRuleVerifier) {}
    override fun exec(element: T): List<RuleResult> = listOf()
    override fun default(): Spec<T>
}

