package org.archguard.comate.token

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LlmTokenCalculateTest {
    @Test
    fun calculate() {
        val calculate = LlmTokenCalculate(100, "gpt-3.5-turbo")
        val result = calculate.calculate("public class Test {}")
        assertEquals(4, result)
    }

    @Test
    fun calculate2() {
        val calculate = LlmTokenCalculate(100, "gpt-3.5")
        assertThrows(IllegalArgumentException::class.java) {
            calculate.calculate("public class Test {}")
        }
    }
}