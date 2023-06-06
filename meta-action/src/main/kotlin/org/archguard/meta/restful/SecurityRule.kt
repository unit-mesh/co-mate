package org.archguard.meta.restful

import org.archguard.meta.ApiRule
import org.archguard.meta.Element

class SecurityRule(val rule: String) : ApiRule("security") {
    override fun exec(input: Element): Any {
        println("exec: ${this.name}")
        return ""
    }
}