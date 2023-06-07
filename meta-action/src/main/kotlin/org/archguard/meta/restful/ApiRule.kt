package org.archguard.meta.restful

import org.archguard.meta.AtomicAction
import org.archguard.meta.AtomicDsl

@AtomicDsl
abstract class ApiRule(val name: String) : AtomicAction {
    abstract fun exec(input: RestApi): Any
}

