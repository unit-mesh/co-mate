package org.archguard.meta.base

interface PatternWithExampleRule<T> : AtomicAction<T> {
    fun pattern(regex: String)
    fun example(sample: String)
    override fun exec(input: T): Boolean {
        return false
    }
}
