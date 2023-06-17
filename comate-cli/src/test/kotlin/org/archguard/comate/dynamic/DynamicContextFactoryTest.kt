package org.archguard.comate.dynamic

import org.archguard.comate.dynamic.functions.AnalysisSystem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DynamicContextFactoryTest {
    @Test
    fun should_get_analysis_system_function() {
        val analysisSystem = AnalysisSystem("http://localhost:8080")
        val defineFunc = analysisSystem.define()
        assertEquals("analysis_system(url: String)", defineFunc)
    }

    @Test
    fun should_read_all_context_functions() {
        val dynamicContextFactory = DynamicContextFactory()
        val functions = dynamicContextFactory.functions()

//        assertEquals(2, functions.size)
//        assertEquals(functions.contains("analysis_system"), true)
    }
}