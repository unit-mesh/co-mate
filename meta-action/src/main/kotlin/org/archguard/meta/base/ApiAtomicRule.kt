package org.archguard.meta.base

import org.archguard.meta.dsl.restful.RestApi

@AtomicDsl
abstract class ApiAtomicRule(open val name: String, open var rule: String) : AtomicAction {
    abstract fun exec(input: RestApi): Any
}

