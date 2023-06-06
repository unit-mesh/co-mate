package org.archguard.meta.restful

import org.archguard.meta.ApiRule
import org.archguard.meta.Element

class HttpActionRule(val actions: List<String>) : ApiRule("http-action") {
    override fun exec(input: Element): Any {
        println("exec: ${this.name}")
        return ""
    }
}