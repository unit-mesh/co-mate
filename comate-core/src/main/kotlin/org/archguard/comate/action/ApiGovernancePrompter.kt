package org.archguard.comate.action

import org.archguard.comate.action.model.ApiResult
import org.archguard.comate.command.ComateContext
import org.archguard.comate.governance.ApiRuleVerifier
import org.archguard.comate.strategy.CodePromptStrategy
import org.archguard.comate.strategy.Strategy
import org.archguard.spec.element.RestApiElement
import org.archguard.spec.lang.RestApiSpec
import org.archguard.spec.lang.rest_api
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ApiGovernancePrompter(
    val context: ComateContext,
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
        val apis = context.fetchApis()

        logger.info("finished analyse code: ${context.workdir}")

        // todo: we don't have enough time to analyse all the apis
        val oneApis: List<RestApiElement> = if (apis.size > 1) {
            apis.subList(0, 1)
        } else {
            apis
        }

        val governance = if (context.spec != null && context.spec!!.javaClass == RestApiSpec::class.java) {
            context.spec as RestApiSpec
        } else {
            RestApiSpec.defaultSpec()
        }

        governance.setVerifier(ApiRuleVerifier(context.connector!!))

        // todo: use a better way to get the apis
        val results = oneApis.map { api ->
            val result = governance.exec(api)
            ApiResult(api.uri, result)
        }

        return """results: ${results.joinToString("\n") { "${it.apiUri}: ${it.result}" }}""".trimIndent()
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ApiGovernancePrompter::class.java)!!
    }
}

