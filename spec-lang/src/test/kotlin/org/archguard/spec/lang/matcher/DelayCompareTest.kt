package org.archguard.spec.lang.matcher

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DelayCompareTest {
    @Test
    fun should_get_correct_to_string() {
        val delayCompare = DelayCompare("test", CompareType.ENDS_WITH, listOf("t"))
        assertEquals("test shouldBe endsWith(\"t\")", delayCompare.toString())
    }

    @Test
    fun should_get_multiple_string_for_array() {
        val delayCompare = DelayCompare("test", CompareType.STARTS_WITH, listOf("t", "s"))
        assertEquals("test shouldBe startsWith(\"t\", \"s\")", delayCompare.toString())
    }
}