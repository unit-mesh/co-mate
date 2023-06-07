package org.archguard.meta.restful

import org.archguard.meta.ApiRule
import org.archguard.meta.RestApi

class StatusCodeRule(val codes: List<Int>) : ApiRule("status-code") {
    override fun exec(input: RestApi): Boolean {
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