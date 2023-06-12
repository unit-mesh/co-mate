package org.archguard.meta.base

import org.archguard.meta.dsl.restful.RestApi


@DslMarker
annotation class AtomicDsl

interface AtomicAction<T> {
    fun exec(input: T): Any {
        return false
    }
}

open class Element

