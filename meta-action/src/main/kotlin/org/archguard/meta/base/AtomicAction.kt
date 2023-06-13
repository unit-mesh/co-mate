package org.archguard.meta.base

import org.archguard.meta.dsl.foundation.Naming

@DslMarker
annotation class SpecDsl

interface AtomicAction<T> {
    val name: String
    fun exec(input: T): Any {
        return false
    }
}

interface BaseLlmVerifier<T : Any> : AtomicAction<T> {
    override fun exec(input: T): List<RuleResult> {
        return exec(input)
    }
}

interface Element

