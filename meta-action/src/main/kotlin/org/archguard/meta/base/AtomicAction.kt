package org.archguard.meta.base

@DslMarker
annotation class SpecDsl

interface AtomicAction<T> {
    fun exec(input: T): Any {
        return false
    }
}

open class Element

