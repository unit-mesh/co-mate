package org.archguard.spec.lang.restapi

import org.archguard.spec.base.Rule
import org.archguard.spec.base.RuleResult
import org.archguard.spec.model.RestApiElement

abstract class ApiAtomicRule(override val actionName: String, open var rule: String) : Rule<RestApiElement> {
    abstract override fun exec(input: RestApiElement): List<RuleResult>
}

