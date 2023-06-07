package org.archguard.meta.restful

import org.archguard.meta.ApiRule
import org.archguard.meta.RestApi

class MiscRule(private val ruleContent: String) : ApiRule("security") {
    override fun exec(input: RestApi): Boolean {
        println("exec: ${this.name}")
        return true
    }
}