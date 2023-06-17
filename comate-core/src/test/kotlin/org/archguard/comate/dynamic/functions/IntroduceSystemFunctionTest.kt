package org.archguard.comate.dynamic.functions

import org.archguard.comate.command.ComateContext
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class IntroduceSystemFunctionTest {
    @Test
    fun should_get_analysis_system_function() {
        val context = ComateContext(Path("."), "kotlin", null)
        val introduceSystemFunction = IntroduceSystemFunction(context)

        val defineFunc = introduceSystemFunction.define()
        assertEquals("introduce_system()", defineFunc)
    }
}