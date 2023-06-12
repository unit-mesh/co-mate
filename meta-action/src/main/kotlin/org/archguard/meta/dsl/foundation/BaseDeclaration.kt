package org.archguard.meta.dsl.foundation

import org.archguard.meta.base.AtomicAction

interface BaseDeclaration<T> {
    fun rules(): List<AtomicAction<T>>
}