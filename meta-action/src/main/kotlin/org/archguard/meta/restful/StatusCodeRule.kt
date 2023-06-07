package org.archguard.meta.restful

import org.archguard.meta.ApiRule
import org.archguard.meta.Element
import org.archguard.meta.RestApi

class StatusCodeRule(val codes: List<Int>) : ApiRule("status-code") {
    override fun exec(input: RestApi): Boolean {
        println("exec: ${this.name}")
        return true
    }
}