package org.archguard.comate.action

import org.archguard.comate.strategy.CodePromptStrategy
import org.archguard.comate.strategy.Strategy
import org.archguard.comate.wrapper.ComateArchGuardClient
import org.archguard.comate.wrapper.ComateSourceCodeContext
import org.archguard.meta.FakeRuleVerifier
import org.archguard.meta.dsl.rest_api
import org.archguard.meta.restful.RestApi
import org.archguard.scanner.analyser.ApiCallAnalyser
import org.archguard.scanner.core.sourcecode.ContainerService
import org.slf4j.LoggerFactory

data class ApiResult(val apiUri: String, val result: Map<String, Boolean>)

class ApiGovernancePrompter(
    val context: CommandContext,
    override val strategy: Strategy,
) : CodePromptStrategy {
    override fun getRole(): String = "Architecture"
    override fun getInstruction(): String = "根据下面的信息，总结 API 的规范情况。"

    override fun getExtendData(): String {
        val client = ComateArchGuardClient()

        logger.info("start to analyse code: $context")
        val codeContext = ComateSourceCodeContext.custom(
            client,
            context.workdir.toString(),
            context.lang,
            features = listOf()
        )

        val codeDataStructs = codeAnalyser(context.lang, codeContext)?.analyse()
        val services: List<ContainerService> = if (codeDataStructs != null) {
            ApiCallAnalyser(codeContext).analyse(codeDataStructs)
        } else {
            listOf()
        }

        logger.info("finished analyse code: ${context.workdir}")

        val apis = services.flatMap {
            it.resources.map { resource ->
                RestApi(
                    uri = resource.sourceUrl,
                    action = resource.sourceHttpMethod.uppercase(),
                    statusCodes = listOf(200)
                )
            }
        }

        val results = apis.map { api ->
            val governance = rest_api {
                uri_construction {
                    rule("\\/api\\/[a-zA-Z0-9]+\\/v[0-9]+\\/[a-zA-Z0-9\\/\\-]+")
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

            governance.context(FakeRuleVerifier())
            val result = governance.exec(api)

            ApiResult(api.uri, result)
        }

        println(results)

        val introduction = this.introduction(context.workdir)

        throw NotImplementedError()

        return """$introduction

""".trimIndent()
    }

    companion object {
        val logger = LoggerFactory.getLogger(ApiGovernancePrompter::class.java)
    }
}