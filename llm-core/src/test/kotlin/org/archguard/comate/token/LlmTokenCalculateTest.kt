package org.archguard.comate.token

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LlmTokenCalculateTest {
    @Test
    fun should_success_when_calculate() {
        val calculate = LlmTokenCalculate(100, "gpt-3.5-turbo")
        val result = calculate.calculate("public class Test {}")
        assertEquals(4, result)
    }

    @Test
    fun should_throw_exception_when_model_not_exist() {
        val calculate = LlmTokenCalculate(100, "gpt-3.5")
        assertThrows(IllegalArgumentException::class.java) {
            calculate.calculate("public class Test {}")
        }
    }
}