package org.archguard.meta.restful.rule

import org.archguard.meta.restful.AtomicRule
import org.archguard.meta.restful.RestApi

class HttpActionRule(private val actions: List<String>) : AtomicRule("http-action", "supported http actions: ${actions.joinToString(", ")}") {
    override fun exec(input: RestApi): Boolean {
        return actions.contains(input.action)
    }
}