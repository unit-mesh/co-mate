package org.archguard.comate.dynamic

import org.archguard.comate.command.ComateContext
import org.archguard.comate.command.fakeComateContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.test.assertNotNull

class DynamicContextFactoryTest {
    @Test
    fun should_read_all_context_functions() {
        val context = fakeComateContext()
        val dynamicContextFactory = DynamicContextFactory(context)
        val functions = dynamicContextFactory.functions()

        assertEquals(functions.contains("introduce_system()"), true)
    }


    @Test
    fun should_find_by_function_name() {
        val context = fakeComateContext()
        val dynamicContextFactory = DynamicContextFactory(context)

        val introduceSystemFunction = dynamicContextFactory.findByName("introduce_system")
        assertNotNull(introduceSystemFunction)
    }
}