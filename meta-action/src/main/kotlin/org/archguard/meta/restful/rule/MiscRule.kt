package org.archguard.meta.restful.rule

import org.archguard.meta.restful.ApiRule
import org.archguard.meta.restful.RestApi

class MiscRule(private val ruleContent: String) : ApiRule("security") {
    override fun exec(input: RestApi): Boolean {
        println("exec: ${this.name}")
        return true
    }
}