package org.archguard.meta.dsl.restful.rule

import org.archguard.meta.base.ApiAtomicRule
import org.archguard.meta.base.RuleResult
import org.archguard.meta.model.RestApiElement

class HttpActionRule(private val actions: List<String>) : ApiAtomicRule("http-action", "supported http actions: ${actions.joinToString(", ")}") {
    override fun exec(input: RestApiElement): RuleResult {
        return RuleResult(this.name, this.rule, actions.contains(input.httpAction))
    }
}