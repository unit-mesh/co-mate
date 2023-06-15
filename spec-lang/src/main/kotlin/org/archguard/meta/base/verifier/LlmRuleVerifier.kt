package org.archguard.meta.base.verifier

/**
 * Use LLM to verify the rule
 */
interface LlmRuleVerifier {
    fun check(prompt: String, input: String): Boolean
}

class FakeRuleVerifier : LlmRuleVerifier {
    override fun check(prompt: String, input: String): Boolean {
        return true
    }
}