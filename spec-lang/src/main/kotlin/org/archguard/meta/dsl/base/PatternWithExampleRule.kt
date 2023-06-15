package org.archguard.meta.dsl.base

import org.archguard.meta.base.Rule
import org.archguard.meta.base.RuleResult

interface PatternWithExampleRule<T> : Rule<T> {
    fun pattern(regex: String)
    fun example(sample: String)
    override fun exec(input: T): List<RuleResult> {
        return listOf()
    }
}
