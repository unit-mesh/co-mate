package org.archguard.meta.dsl.foundation

import org.archguard.meta.base.AtomicRule

interface BaseDeclaration {
    fun rules(): List<AtomicRule>
}