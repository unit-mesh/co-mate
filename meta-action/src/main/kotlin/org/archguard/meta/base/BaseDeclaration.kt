package org.archguard.meta.base

interface BaseDeclaration<T> {
    fun rules(element: T): List<Rule<T>>
}