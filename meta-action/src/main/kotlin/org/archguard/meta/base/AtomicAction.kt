package org.archguard.meta.base

@DslMarker
annotation class SpecDsl

interface AtomicAction<T> {
    val name: String
    fun exec(input: T): Any {
        return false
    }
}

interface Element

