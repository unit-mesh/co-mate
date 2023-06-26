package org.archguard.architecture.action

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IOActionTypeTest {
    @Test
    fun should_get_class_by_value_of() {
        assertEquals(IOActionType.CREATE_DIRECTORY, IOActionType.from("CREATE_DIRECTORY"))
        assertEquals(IOActionType.CREATE_FILE, IOActionType.from("CREATE_FILE"))
        assertEquals(IOActionType.CREATE_PACKAGE, IOActionType.from("CREATE_PACKAGE"))
        assertEquals(IOActionType.CREATE_CLASS, IOActionType.from("CREATE_CLASS"))
        assertEquals(IOActionType.CREATE_INTERFACE, IOActionType.from("CREATE_INTERFACE"))
        assertEquals(IOActionType.CREATE_ENUM, IOActionType.from("CREATE_ENUM"))

        assertEquals(IOActionType.UNKNOWN, IOActionType.from("NOT_EXIST"))
    }

}