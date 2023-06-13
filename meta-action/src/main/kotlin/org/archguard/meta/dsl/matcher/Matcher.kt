package org.archguard.meta.dsl.matcher

import org.archguard.meta.dsl.foundation.DelayCompare
import java.io.Serializable

@Suppress("UNCHECKED_CAST")
infix fun <T, U : T> T.shouldBe(expected: DelayCompare?): Serializable? {
    if (expected != null) {
        expected.equal = true
    }

    return false
}

@Suppress("UNCHECKED_CAST")
infix fun <T> T.shouldNotBe(any: DelayCompare?): Serializable? {
    if (any != null) {
        any.equal = false
    }

    return false
}
