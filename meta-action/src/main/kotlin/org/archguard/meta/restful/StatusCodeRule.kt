package org.archguard.meta.restful

import org.archguard.meta.ApiRule
import org.archguard.meta.Element

class StatusCodeRule(val codes: List<Int>) : ApiRule("status-code") {
    override fun exec(input: Element): Any {
        println("exec: ${this.name}")
        return ""
    }
}