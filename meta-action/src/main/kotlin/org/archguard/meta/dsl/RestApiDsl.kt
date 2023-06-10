package org.archguard.meta.dsl

import kotlinx.serialization.Serializable
import org.archguard.meta.FakeRuleVerifier
import org.archguard.meta.LlmRuleVerifier
import org.archguard.meta.restful.ApiCheckRule
import org.archguard.meta.restful.ApiRule
import org.archguard.meta.restful.RestApi
import org.archguard.meta.restful.rule.*

// todo: is for checking the rule type
enum class ApiRuleType(rule: Class<out ApiRule>) {
    HTTP_ACTION(HttpActionRule::class.java),
    MISC(MiscRule::class.java),
    SECURITY(SecurityRule::class.java),
    STATUS_CODE(StatusCodeRule::class.java),
    URI_CONSTRUCTION(UriConstructionRule::class.java)
}

@Serializable
data class ApiRuleResult(val ruleName: String, val rule: String, val result: Boolean)

class RestApiDsl {
    private var ruleVerifier: LlmRuleVerifier = FakeRuleVerifier()
    private var rules: List<ApiRule> = listOf()

    private var needUpdateContextRules: List<ApiRule> = listOf()

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
        val securityRule = SecurityRule(security, ruleVerifier)
        needUpdateContextRules += securityRule
        rules = rules + securityRule
        return securityRule
    }

    fun misc(misc: String): MiscRule {
        val miscRule = MiscRule(misc, ruleVerifier)
        needUpdateContextRules += miscRule
        rules = rules + miscRule
        return miscRule
    }

    fun exec(element: RestApi): Map<String, ApiRuleResult> {
        return rules.associate { it.name to ApiRuleResult(it.name, it.rule, it.exec(element) as Boolean) }
    }

    fun context(ruleVerifier: LlmRuleVerifier) {
        this.ruleVerifier = ruleVerifier
        needUpdateContextRules.forEach {
            if (it is ApiCheckRule) {
                it.ruleVerifier = ruleVerifier
            }
        }
    }
}

fun rest_api(init: RestApiDsl.() -> Unit): RestApiDsl {
    val html = RestApiDsl()
    html.init()
    return html
}
