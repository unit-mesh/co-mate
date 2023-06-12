package org.archguard.meta.base

import org.archguard.meta.model.RestApiElement

@SpecDsl
abstract class ApiAtomicRule(override val name: String, open var rule: String) : AtomicAction<RestApiElement> {
    abstract override fun exec(input: RestApiElement): Any
}

