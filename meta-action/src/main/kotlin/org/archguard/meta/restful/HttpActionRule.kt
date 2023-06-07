package org.archguard.meta.restful

import org.archguard.meta.ApiRule
import org.archguard.meta.RestApi

class HttpActionRule(private val actions: List<String>) : ApiRule("http-action") {
    override fun exec(input: RestApi): Boolean {
        return actions.contains(input.action)
    }
}