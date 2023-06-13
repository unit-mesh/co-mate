package org.archguard.meta.dsl.restful.rule

import org.archguard.meta.base.RuleResult
import org.archguard.meta.dsl.restful.ApiAtomicRule
import org.archguard.meta.model.RestApiElement

class StatusCodeRule(private val codes: List<Int>) : ApiAtomicRule("status-code", "supported codes: ${codes.joinToString(", ")}") {
    override fun exec(input: RestApiElement): List<RuleResult> {
        if (input.statusCodes.isEmpty()) {
            return listOf(RuleResult(this.actionName, this.rule, false))
        }

        val statusCode = input.statusCodes

        val isEqual = if (statusCode.size == 1) {
            statusCode[0] == codes[0]
        } else {
            statusCode.containsAll(codes)
        }

        return listOf(RuleResult(this.actionName, this.rule, isEqual))
    }
}