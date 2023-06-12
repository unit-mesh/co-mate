package org.archguard.meta.matcher

import org.archguard.meta.dsl.MatcherResult

interface Matcher<in T> {
    fun test(value: T): MatcherResult

    fun invert(): Matcher<T> = Matcher {
        with(test(it)) {
            MatcherResult(!passed(), { negatedFailureMessage() }, { failureMessage() })
        }
    }

    companion object {
        fun <T> failure(error: String) = Matcher<T> { MatcherResult.invoke(false, { error }, { "" }) }

        inline operator fun <T> invoke(crossinline tester: (T) -> MatcherResult) = object : Matcher<T> {
            override fun test(value: T) = tester(value)
        }
    }

}

infix fun <T> T.shouldHave(matcher: Matcher<T>) = should(matcher)
infix fun <T> T.should(matcher: Matcher<T>) {
    invokeMatcher(this, matcher)
}

fun <T> invokeMatcher(t: T, matcher: Matcher<T>): T {
    // todo: add support for shouldNot
    matcher.test(t).apply {
        if (!passed()) {
            throw AssertionError(failureMessage())
        }
    }
    return t
}

infix fun <T> T.shouldNotHave(matcher: Matcher<T>) = shouldNot(matcher)
infix fun <T> T.shouldNot(matcher: Matcher<T>) = should(matcher.invert())
infix fun <T> T.should(matcher: (T) -> Unit) = matcher(this)