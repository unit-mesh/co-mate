package org.archguard.comate.governance

import org.archguard.comate.smart.LlmConnector
import org.archguard.meta.LlmRuleVerifier

class ApiRuleVerifier(val connector: LlmConnector) : LlmRuleVerifier {
    override fun check(prompt: String, input: String): Boolean {
        val result = connector.prompt(
            """
            | You're a software architect governance expert, please verify the rule with uri. Here is the requirement:
            | 
            | 1. If API is not need authentication, just return true, no explanation.
            | 2. If it is not clear whether authentication is needed, just return true, no explanation.
            | 3. If API is need authentication, please check the authentication type, no explanation.
            | 4. You should only return ###true### or ###false###.
            | 
            | Here is rule:
            | ###
            |$prompt
            | ###
            |
            | Here is the uri information:
            | ###
            | $input
            | ###
        """.trimMargin()
        )

        logger.info("start to verify the rule: $prompt, input: $input, result: $result")

        return result.lowercase() == "true" || result.lowercase().contains("true")
    }

    companion object {
        val logger = org.slf4j.LoggerFactory.getLogger(ApiRuleVerifier::class.java)
    }
}