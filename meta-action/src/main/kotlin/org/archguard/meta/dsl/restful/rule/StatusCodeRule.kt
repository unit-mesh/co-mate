package org.archguard.meta.dsl.restful.rule

import org.archguard.meta.base.AtomicRule
import org.archguard.meta.model.RestApiElement

class StatusCodeRule(private val codes: List<Int>) : AtomicRule("status-code", "supported codes: ${codes.joinToString(", ")}") {
    override fun exec(input: RestApiElement): Boolean {
        if (input.statusCodes.isEmpty()) {
            return false
        }

        val statusCode = input.statusCodes

        return if (statusCode.size == 1) {
            statusCode[0] == codes[0]
        } else {
            statusCode.containsAll(codes)
        }
    }
}