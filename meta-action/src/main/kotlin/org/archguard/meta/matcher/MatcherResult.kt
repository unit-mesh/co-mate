package org.archguard.meta.matcher

interface MatcherResult {
    fun passed(): Boolean
    fun failureMessage(): String

    fun negatedFailureMessage(): String

    companion object {
        operator fun invoke(
            passed: Boolean,
            failureMessageFn: () -> String,
            negatedFailureMessageFn: () -> String,
        ) = object : MatcherResult {
            override fun passed(): Boolean = passed
            override fun failureMessage(): String = failureMessageFn()
            override fun negatedFailureMessage(): String = negatedFailureMessageFn()
        }
    }

}