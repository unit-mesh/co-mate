package org.archguard.comate.action

import org.archguard.comate.action.model.ApiResult
import org.archguard.comate.command.CommandContext
import org.archguard.comate.governance.ApiRuleVerifier
import org.archguard.comate.strategy.CodePromptStrategy
import org.archguard.comate.strategy.Strategy
import org.archguard.comate.wrapper.ComateSourceCodeContext
import org.archguard.meta.dsl.rest_api
import org.archguard.meta.model.RestApiElement
import org.archguard.scanner.analyser.ApiCallAnalyser
import org.archguard.scanner.core.sourcecode.ContainerService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ApiGovernancePrompter(
    val context: CommandContext,
    override val strategy: Strategy,
) : CodePromptStrategy {
    override fun getRole(): String = "Architecture Governance Expert"
    override fun getInstruction(): String = "根据下面的信息，总结 RESTful API 的规范情况。"

    override fun getRequirements(): String = """
1. 请使用中文返回。
2. API 应该符合基本 RESTful API 的规范，如 URI 构造、采用标准的 HTTP 方法、状态码、安全等。
3. 如果 result 是 true，请不要返回任何信息。
4. 如果 result 是 false，请返回不通过的原因，并根据 rule 提供符合规范的 API。
5. 你只返回如下的结果：

###
- API `{api uri}` 不符合 { rule name } 规范，Rule: { rule }，建议 API 修改为 {new api}。
###
"""

    override fun getExtendData(): String {
        logger.info("start to analyse code: $context")
        val codeContext = ComateSourceCodeContext.create(context)

        val codeDataStructs = codeAnalyser(context.language, codeContext)?.analyse()
        val services: List<ContainerService> = if (codeDataStructs != null) {
            ApiCallAnalyser(codeContext).analyse(codeDataStructs)
        } else {
            listOf()
        }

        logger.info("finished analyse code: ${context.workdir}")

        val apis = services.flatMap {
            it.resources.map { resource ->
                RestApiElement(
                    uri = resource.sourceUrl,
                    httpAction = resource.sourceHttpMethod.uppercase(),
                    statusCodes = listOf(200)
                )
            }
        }

        // todo: use a better way to get the apis
        val results = apis.map { api ->
            val governance = rest_api {
                uri_construction {
                    pattern("\\/api\\/[a-zA-Z0-9]+\\/v[0-9]+\\/[a-zA-Z0-9\\/\\-]+")
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

            governance.setVerifier(ApiRuleVerifier(context.connector!!))
            val result = governance.exec(api)

            ApiResult(api.uri, result)
        }

        return """results: ${results.joinToString("\n") { "${it.apiUri}: ${it.result}" }}""".trimIndent()
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ApiGovernancePrompter::class.java)!!
    }
}