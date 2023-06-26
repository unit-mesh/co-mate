package org.archguard.architecture.layered

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ArchitectureStyleTest {
    @Test
    fun should_get_class_by_value_of() {
        assertEquals(ArchitectureStyle.LAYERED, ArchitectureStyle.from("LAYERED"))
        assertEquals(ArchitectureStyle.PIPELINE, ArchitectureStyle.from("PIPELINE"))

        assertEquals(ArchitectureStyle.UNKNOWN, ArchitectureStyle.from("NOT_EXIST"))
    }

    @Test
    fun should_return_true_when_has_contains() {
        assertTrue(ArchitectureStyle.contains("layered"))
        assertTrue(ArchitectureStyle.contains("PIPELINE"))
    }
}