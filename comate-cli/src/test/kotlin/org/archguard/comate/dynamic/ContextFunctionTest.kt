package org.archguard.comate.dynamic

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ContextFunctionTest {
    @Test
    fun should_read_all_context_functions() {
        val contextFunction = ContextFunction()
        val functions = contextFunction.functions()
        assertEquals(2, functions.size)

        assertEquals(functions.contains("analysis_system"), true)
    }
}