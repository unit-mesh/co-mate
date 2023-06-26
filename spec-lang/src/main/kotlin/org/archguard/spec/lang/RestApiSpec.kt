package org.archguard.spec.lang

import org.archguard.spec.base.verifier.FakeRuleVerifier
import org.archguard.spec.base.verifier.LlmRuleVerifier
import org.archguard.spec.base.RuleResult
import org.archguard.spec.lang.base.Spec
import org.archguard.spec.lang.restapi.ApiAtomicRule
import org.archguard.spec.lang.restapi.ApiLlmVerifyRule
import org.archguard.spec.lang.restapi.rule.*
import org.archguard.spec.element.RestApiElement

@SpecDsl
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

    override fun default(): Spec<RestApiElement> {
        return rest_api {
            uri_construction {
                pattern("/api\\/[a-zA-Z0-9]+\\/v[0-9]+\\/[a-zA-Z0-9\\/\\-]+")
                example("/api/petstore/v1/pets/dogs")
            }

            http_action("GET", "POST", "PUT", "DELETE")
            status_code(200, 201, 202, 204, 400, 401, 403, 404, 500, 502, 503, 504)

            security(
                """
Token Based Authentication (Recommended) Ideally, microservices should be stateless so the service instances can be scaled out easily and the client requests can be routed to multiple independent service providers. A token based authentication mechanism should be used instead of session based authentication
            """.trimIndent()
            )

            misc("""""")
        }
    }
}

fun rest_api(init: RestApiSpec.() -> Unit): RestApiSpec {
    val spec = RestApiSpec()
    spec.init()
    return spec
}
