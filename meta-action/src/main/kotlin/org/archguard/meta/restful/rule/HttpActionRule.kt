package org.archguard.meta.restful.rule

import org.archguard.meta.restful.ApiRule
import org.archguard.meta.restful.RestApi

class HttpActionRule(private val actions: List<String>) : ApiRule("http-action") {
    override fun exec(input: RestApi): Boolean {
        return actions.contains(input.action)
    }
}