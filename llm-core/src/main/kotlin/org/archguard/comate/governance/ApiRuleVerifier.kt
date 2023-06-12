package org.archguard.comate.governance

import org.archguard.comate.smart.LlmConnector
import org.archguard.meta.base.LlmRuleVerifier
import org.slf4j.LoggerFactory

class ApiRuleVerifier(val connector: LlmConnector) : LlmRuleVerifier {
    override fun check(prompt: String, input: String): Boolean {
        if (prompt.isBlank() || input.isBlank()) {
            return true
        }

        val finalPrompt = """
            | You're a software architect governance expert, please verify the rule with APIs. Here is the requirement:
            | 
            | 1. If APIs is not need authentication, just return true, no explanation.
            | 2. If it is not clear whether authentication is needed, just return true, no explanation.
            | 3. If APIs is need authentication, please check the authentication type, no explanation.
            | 4. You should only return ###true### or ###false###, no explanation.
            | 5. Here is your output format, please follow it:
            | ###
            | | API  | result |
            | | ---- | ------ |
            | | {http action} {uri} | {true or false} |
            | ###
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
        val result = connector.prompt(finalPrompt)

        logger.info("start to verify the rule: $finalPrompt result: $result")

        return result.lowercase() == "true" || result.lowercase().contains("true")
    }

    companion object {
        val logger = LoggerFactory.getLogger(ApiRuleVerifier::class.java)!!
    }
}