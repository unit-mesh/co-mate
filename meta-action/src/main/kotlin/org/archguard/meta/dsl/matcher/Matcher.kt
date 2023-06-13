package org.archguard.meta.dsl.matcher

import io.kotest.matchers.Matcher
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot

@Suppress("UNCHECKED_CAST")
infix fun <T, U : T> T.shouldBe(expected: U?) {
    when (expected) {
        is Matcher<*> -> should(expected as Matcher<T>)
        else -> {
            val actual = this
//            eq(actual, expected)?.let(errorCollector::collectOrThrow)
        }
    }
}

@Suppress("UNCHECKED_CAST")
infix fun <T> T.shouldNotBe(any: Any?): T {
    when (any) {
        is Matcher<*> -> shouldNot(any as Matcher<T>)
        else -> {
//            shouldNot(equalityMatcher(any))
        }
    }

    return this
}
