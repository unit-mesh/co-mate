package org.archguard.comate.dynamic.functions

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IntroduceSystemFunctionTest {
    @Test
    fun should_get_analysis_system_function() {
        val introduceSystemFunction = IntroduceSystemFunction("http://localhost:8080")
        val defineFunc = introduceSystemFunction.define()
        assertEquals("introduce_system(url: String)", defineFunc)
    }
}