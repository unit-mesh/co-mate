package org.archguard.comate.server.action

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ToolingActionTest {
    @Test
    fun should_get_class_by_value_of() {
        assertEquals(ToolingAction.INTRODUCE_SYSTEM, ToolingAction.from("INTRODUCE_SYSTEM"))
        assertEquals(ToolingAction.REST_API_GOVERNANCE, ToolingAction.from("REST_API_GOVERNANCE"))
    }

    @Test
    fun should_handle_by_null() {
        assertEquals(null, ToolingAction.from("NOT_EXIST"))
    }
}