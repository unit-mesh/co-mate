package org.archguard.meta

import org.archguard.meta.restful.HttpAtomicActionRule
import org.archguard.meta.restful.SecurityRule
import org.archguard.meta.restful.StatusCodeRule
import org.archguard.meta.restful.UriConstructionRule


class ApiGovernance {
    var rules: List<ApiRule> = listOf()

    fun uri_construction(function: UriConstructionRule.() -> Unit): UriConstructionRule {
        val html = UriConstructionRule()
        html.function()
        return html
    }

    fun http_action(vararg actions: String): HttpAtomicActionRule {
        val httpActionRule = HttpAtomicActionRule(actions.toList())
        rules = rules + httpActionRule
        return httpActionRule
    }

    fun status_code(vararg codes: Int): StatusCodeRule {
        val statusCodeRule = StatusCodeRule(codes.toList())
        rules = rules + statusCodeRule
        return statusCodeRule
    }

    fun security(security: String): SecurityRule {
        val securityRule = SecurityRule(security)
        rules = rules + securityRule
        return securityRule
    }

    fun exec(element: RestApi) {
        rules.forEach {
            it.exec(element)
        }
    }
}

fun api_governance(init: ApiGovernance.() -> Unit): ApiGovernance {
    val html = ApiGovernance()
    html.init()
    return html
}
