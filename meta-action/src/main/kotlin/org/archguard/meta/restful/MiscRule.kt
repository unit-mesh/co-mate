package org.archguard.meta.restful

import org.archguard.meta.ApiRule
import org.archguard.meta.Element

class MiscRule(val ruleContent: String) : ApiRule("security") {
    override fun exec(input: Element): Any {
        println("exec: ${this.name}")
        return ""
    }
}