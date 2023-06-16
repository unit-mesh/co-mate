package org.archguard.spec.base

interface LlmRule<T : Any> : Rule<T> {
    override fun exec(input: T): List<RuleResult> {
        return exec(input)
    }
}