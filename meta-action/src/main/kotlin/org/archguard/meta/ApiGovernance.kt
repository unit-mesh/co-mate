package org.archguard.meta



class UriConstruction: ApiRule("uri-construction") {
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

class StatusCode: ApiRule("status-code") {
    var codes = listOf<Int>()

    fun codes(vararg codes: Int) {
        this.codes = codes.toList()
    }

    override fun exec(input: Element): Any {
        println("exec: ${this.name}")
        return ""
    }
}

class ApiGovernance {
    var http_action = listOf<String>()
    var security = ""

    fun uri_construction(function: UriConstruction.() -> Unit): UriConstruction {
        val html = UriConstruction()
        html.function()
        return html
    }

    fun http_action(vararg actions: String) {
        this.http_action = actions.toList()
    }

    fun status_code(vararg codes: Int): StatusCode {
        val action = StatusCode()
        action.codes(*codes)
        return action
    }

    fun security(securityRule: String) {
        this.security = securityRule
    }
}

fun api_governance(init: ApiGovernance.() -> Unit): ApiGovernance {
    val html = ApiGovernance()
    html.init()
    return html
}
