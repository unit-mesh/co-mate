package org.archguard.meta.dsl.restful.rule

import org.archguard.meta.base.ApiAtomicRule
import org.archguard.meta.dsl.restful.RestApi

class HttpActionRule(private val actions: List<String>) : ApiAtomicRule("http-action", "supported http actions: ${actions.joinToString(", ")}") {
    override fun exec(input: RestApi): Boolean {
        return actions.contains(input.action)
    }
}