package org.archguard.comate.dynamic

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ContextFunctionTest {
    @Test
    fun should_read_all_context_functions() {
        val contextFunction = ContextFunction()
        val functions = contextFunction.functions()
        assertEquals(1, functions.size)

        assertEquals(functions[0], "analysis_system")
    }
}