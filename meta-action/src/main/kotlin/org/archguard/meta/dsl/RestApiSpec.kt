package org.archguard.meta.dsl

import org.archguard.meta.base.*
import org.archguard.meta.dsl.restful.ApiLlmVerifyRule
import org.archguard.meta.model.RestApiElement
import org.archguard.meta.dsl.restful.rule.*

// todo: is for checking the rule type
enum class ApiRuleType(rule: Class<out ApiAtomicRule>) {
    HTTP_ACTION(HttpActionRule::class.java),
    MISC(MiscRule::class.java),
    SECURITY(SecurityRule::class.java),
    STATUS_CODE(StatusCodeRule::class.java),
    URI_CONSTRUCTION(UriConstructionRule::class.java)
}

class RestApiSpec : Spec<RestApiElement> {
    private var ruleVerifier: LlmRuleVerifier = FakeRuleVerifier()
    private var rules: List<ApiAtomicRule> = listOf()
    private var needUpdateContextRules: List<ApiAtomicRule> = listOf()

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

    override fun exec(element: RestApiElement): List<RuleResult> =
        rules
            .map { rule -> rule.exec(element) }
            .flatten()

    override fun setVerifier(ruleVerifier: LlmRuleVerifier) {
        this.ruleVerifier = ruleVerifier
        needUpdateContextRules.forEach {
            if (it is ApiLlmVerifyRule) {
                it.ruleVerifier = ruleVerifier
            }
        }
    }
}

fun rest_api(init: RestApiSpec.() -> Unit): RestApiSpec {
    val spec = RestApiSpec()
    spec.init()
    return spec
}
