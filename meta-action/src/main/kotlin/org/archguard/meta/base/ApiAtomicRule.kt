package org.archguard.meta.base

import org.archguard.meta.dsl.restful.RestApi

@SpecDsl
abstract class ApiAtomicRule(open val name: String, open var rule: String) : AtomicAction<RestApi> {
    abstract override fun exec(input: RestApi): Any
}

