package org.archguard.spec.lang.matcher

import java.io.Serializable

infix fun <T, U : T> T.shouldBe(expected: DelayCompare?): Serializable? {
    if (expected != null) {
        expected.equal = true
    }

    return ""
}

infix fun <T> T.shouldNotBe(any: DelayCompare?): Serializable? {
    if (any != null) {
        any.equal = false
    }

    return ""
}
