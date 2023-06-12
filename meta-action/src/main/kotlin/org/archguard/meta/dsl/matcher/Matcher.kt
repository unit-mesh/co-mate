package org.archguard.meta.dsl.matcher

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


@Suppress("UNCHECKED_CAST")
infix fun <T, U : T> T.shouldBe(expected: U?) {
    when (expected) {
        is Matcher<*> -> should(expected as Matcher<T>)
        else -> {
//            val actual = this
//            eq(actual, expected)?.let(errorCollector::collectOrThrow)
        }
    }
}

@Suppress("UNCHECKED_CAST")
infix fun <T> T.shouldNotBe(any: Any?): T {
    when (any) {
//        is Matcher<*> -> shouldNot(any as Matcher<T>)
//        else -> shouldNot(equalityMatcher(any))
    }
    return this
}

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
