package org.archguard.comate.dynamic.functions

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IntroductionSystemTest {
    @Test
    fun should_get_analysis_system_function() {
        val introductionSystem = IntroductionSystem("http://localhost:8080")
        val defineFunc = introductionSystem.define()
        assertEquals("introduction_system(url: String)", defineFunc)
    }
}