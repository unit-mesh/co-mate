package org.archguard.comate.dynamic

import org.junit.jupiter.api.Test

class DynamicContextFactoryTest {
    @Test
    fun should_read_all_context_functions() {
        val dynamicContextFactory = DynamicContextFactory()
        val functions = dynamicContextFactory.functions()

        println(functions)

//        assertEquals(2, functions.size)
//        assertEquals(functions.contains("analysis_system"), true)
    }
}