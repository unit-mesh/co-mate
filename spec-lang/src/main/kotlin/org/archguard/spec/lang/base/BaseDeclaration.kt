package org.archguard.spec.lang.base

import org.archguard.spec.base.Rule

interface BaseDeclaration<T> {
    fun rules(element: T): List<Rule<T>> {
        return listOf()
    }
}