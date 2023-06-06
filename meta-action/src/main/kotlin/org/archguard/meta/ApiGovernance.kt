package org.archguard.meta


class UriConstructionRule : ApiRule("uri-construction") {
    var ruleRegex: Regex? = null
    var sample = ""

    fun rule(regex: String) {
        this.ruleRegex = Regex(regex)
    }

    fun sample(sample: String) {
        this.sample = sample
    }

    override fun exec(input: Element): Any {
        println("exec: ${this.name}")
        return ""
    }
}

class StatusCodeRule(val codes: List<Int>) : ApiRule("status-code") {
    override fun exec(input: Element): Any {
        println("exec: ${this.name}")
        return ""
    }
}

class HttpActionRule(val actions: List<String>) : ApiRule("http-action") {
    override fun exec(input: Element): Any {
        println("exec: ${this.name}")
        return ""
    }
}


class SecurityRule(val rule: String) : ApiRule("security") {
    override fun exec(input: Element): Any {
        println("exec: ${this.name}")
        return ""
    }
}

class ApiGovernance {
    var rules: List<ApiRule> = listOf()

    fun uri_construction(function: UriConstructionRule.() -> Unit): UriConstructionRule {
        val html = UriConstructionRule()
        html.function()
        return html
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
