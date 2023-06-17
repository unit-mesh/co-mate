package org.archguard.comate.dynamic

import org.archguard.comate.command.ComateContext
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class DynamicContextFactoryTest {
    @Test
    fun should_read_all_context_functions() {
        val context = ComateContext(Path("."), "kotlin", null)
        val dynamicContextFactory = DynamicContextFactory(context)
        val functions = dynamicContextFactory.functions()

        println(functions)

//        assertEquals(2, functions.size)
//        assertEquals(functions.contains("analysis_system"), true)
    }


    @Test
    fun should_return_all_explains() {

    }
}