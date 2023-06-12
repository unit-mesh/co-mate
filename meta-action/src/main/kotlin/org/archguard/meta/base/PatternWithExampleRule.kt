package org.archguard.meta.base

interface PatternWithExampleRule: AtomicAction {
    fun pattern(regex: String)
    fun example(sample: String)
}
