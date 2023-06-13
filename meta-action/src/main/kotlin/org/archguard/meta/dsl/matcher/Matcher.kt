package org.archguard.meta.dsl.matcher

import io.kotest.assertions.eq.eq
import io.kotest.matchers.Matcher
import io.kotest.matchers.equalityMatcher
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import java.io.Serializable

@Suppress("UNCHECKED_CAST")
infix fun <T, U : T> T.shouldBe(expected: U?): Serializable? {
    when (expected) {
        is Matcher<*> -> should(expected as Matcher<T>)
        else -> {
            val actual = this
            return eq(actual, expected)?.let { it }
        }
    }

    return false
}

@Suppress("UNCHECKED_CAST")
infix fun <T> T.shouldNotBe(any: Any?): Serializable {
    when (any) {
        is Matcher<*> -> shouldNot(any as Matcher<T>)
        else -> {
            shouldNot(equalityMatcher(any))
        }
    }

    return false
}
