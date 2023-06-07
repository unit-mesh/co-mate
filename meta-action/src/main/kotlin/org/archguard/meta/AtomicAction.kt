package org.archguard.meta


@DslMarker
annotation class AtomicDsl

interface AtomicAction {
}

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


open class Element

