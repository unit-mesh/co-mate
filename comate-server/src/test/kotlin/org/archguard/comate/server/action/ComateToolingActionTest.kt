package org.archguard.comate.server.action

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ComateToolingActionTest {
    @Test
    fun should_get_class_by_value_of() {
        assertEquals(ComateToolingAction.INTRODUCE_SYSTEM, ComateToolingAction.from("INTRODUCE_SYSTEM"))
        assertEquals(ComateToolingAction.REST_API_GOVERNANCE, ComateToolingAction.from("REST_API_GOVERNANCE"))
    }

    @Test
    fun should_handle_by_null() {
        assertEquals(null, ComateToolingAction.from("NOT_EXIST"))
    }
}