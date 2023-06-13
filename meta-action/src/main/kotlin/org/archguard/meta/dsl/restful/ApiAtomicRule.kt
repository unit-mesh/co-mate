package org.archguard.meta.dsl.restful

import org.archguard.meta.base.Rule
import org.archguard.meta.base.RuleResult
import org.archguard.meta.model.RestApiElement

abstract class ApiAtomicRule(override val actionName: String, open var rule: String) : Rule<RestApiElement> {
    abstract override fun exec(input: RestApiElement): List<RuleResult>
}

