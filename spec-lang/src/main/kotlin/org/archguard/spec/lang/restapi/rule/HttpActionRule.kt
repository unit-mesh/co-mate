package org.archguard.spec.lang.restapi.rule

import org.archguard.spec.base.RuleResult
import org.archguard.spec.lang.restapi.ApiAtomicRule
import org.archguard.spec.element.RestApiElement

class HttpActionRule(private val actions: List<String>) : ApiAtomicRule("http-action", "supported http actions: ${actions.joinToString(", ")}") {
    override fun exec(input: RestApiElement): List<RuleResult> {
        return listOf(RuleResult(this.actionName, this.rule, actions.contains(input.httpAction)))
    }
}