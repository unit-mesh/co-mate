package org.archguard.meta.base

import org.archguard.meta.model.RestApiElement

abstract class ApiAtomicRule(override val name: String, open var rule: String) : AtomicAction<RestApiElement> {
    abstract override fun exec(input: RestApiElement): List<RuleResult>
}

