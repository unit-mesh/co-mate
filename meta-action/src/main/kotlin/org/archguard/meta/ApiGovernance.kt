package org.archguard.meta

import org.archguard.meta.restful.*

// todo: is for checking the rule type
enum class ApiRuleType(rule: Class<out ApiRule>) {
    HTTP_ACTION(HttpActionRule::class.java),
    MISC(MiscRule::class.java),
    SECURITY(SecurityRule::class.java),
    STATUS_CODE(StatusCodeRule::class.java),
    URI_CONSTRUCTION(UriConstructionRule::class.java)
}

class ApiGovernance {
    var rules: List<ApiRule> = listOf()

    fun uri_construction(function: UriConstructionRule.() -> Unit): UriConstructionRule {
        val uriRule = UriConstructionRule()
        uriRule.function()
        rules = rules + uriRule
        return uriRule
    }

    fun http_action(vararg actions: String): HttpActionRule {
        val httpActionRule = HttpActionRule(actions.toList())
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
