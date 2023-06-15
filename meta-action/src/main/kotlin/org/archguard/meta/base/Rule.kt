package org.archguard.meta.base

@DslMarker
annotation class SpecDsl

interface Rule<T> {
    val actionName: String
    fun exec(input: T): Any {
        return false
    }
}

interface Element

