package org.archguard.meta.dsl.base

import org.archguard.meta.base.Rule

interface BaseDeclaration<T> {
    fun rules(element: T): List<Rule<T>>
}