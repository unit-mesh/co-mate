package org.archguard.meta.baserule

import org.archguard.meta.AtomicAction

interface PatternWithExampleRule: AtomicAction {
    fun pattern(regex: String)
    fun example(sample: String)
}
