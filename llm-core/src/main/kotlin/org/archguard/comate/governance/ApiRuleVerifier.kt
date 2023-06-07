package org.archguard.comate.governance

import org.archguard.comate.smart.LlmConnector
import org.archguard.meta.LlmRuleVerifier

class ApiRuleVerifier(val connector: LlmConnector): LlmRuleVerifier {
    override fun check(prompt: String, input: String): Boolean {
        val result = connector.prompt(
            """
            | You're a software architect governance expert, please verify the rule. Here is the requirement:
            | 
            | 1. Only return true if the input is true, otherwise return false.
            | 
            | Here is rules
            | ###
            |$prompt
            | ###
            |
            | Here is the input:
            | ###
            | $input
            | ###
        """.trimMargin()
        )

        return result.lowercase() == "true"
    }
}