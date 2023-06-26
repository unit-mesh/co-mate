package org.archguard.comate.server.action

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ToolingActionTest {
    @Test
    fun should_get_class_by_value_of() {
        val valueOf = ToolingAction.valueOf("INTRODUCE_SYSTEM")
        assertEquals(ToolingAction.INTRODUCE_SYSTEM, valueOf)
    }


}