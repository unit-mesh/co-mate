package org.archguard.meta.base

interface BaseDeclaration<T> {
    fun rules(): List<AtomicAction<T>>
}