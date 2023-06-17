package org.archguard.comate.dynamic.functions

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IntroductionSystemFunctionTest {
    @Test
    fun should_get_analysis_system_function() {
        val introductionSystemFunction = IntroductionSystemFunction("http://localhost:8080")
        val defineFunc = introductionSystemFunction.define()
        assertEquals("introduction_system(url: String)", defineFunc)
    }
}