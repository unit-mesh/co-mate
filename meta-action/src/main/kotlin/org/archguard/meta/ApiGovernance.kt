package org.archguard.meta


@DslMarker
annotation class ApiDsl

@ApiDsl
class ApiGovernance {
    var http_action = listOf<String>()
    var status_code = listOf<Int>()
    var security = ""

    fun uri_construction(function: UriConstruction.() -> Unit): UriConstruction {
        val html = UriConstruction()
        html.function()
        return html
    }

    fun http_action(vararg actions: String) {
        this.http_action = actions.toList()
    }

    fun status_code(vararg codes: Int) {
        this.status_code = codes.toList()
    }

    fun security(securityRule: String) {
        this.security = securityRule
    }
}

class UriConstruction {
    var ruleRegex: Regex? = null
    var sample = ""

    fun rule(regex: String) {
        this.ruleRegex = Regex(regex)
    }

    fun sample(sample: String) {
        this.sample = sample
    }

}

fun api_governance(init: ApiGovernance.() -> Unit): ApiGovernance {
    val html = ApiGovernance()
    html.init()
    return html
}
