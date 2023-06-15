package org.archguard.spec.lang.base

import org.archguard.spec.base.Rule
import org.archguard.spec.base.RuleResult

interface PatternWithExampleRule<T> : Rule<T> {
    fun pattern(regex: String)
    fun example(sample: String)
    override fun exec(input: T): List<RuleResult> {
        return listOf()
    }
}
