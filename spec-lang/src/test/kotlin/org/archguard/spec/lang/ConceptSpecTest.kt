package org.archguard.spec.lang

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ConceptSpecTest {
    @Test
    fun should_equal_when_had_same_spec() {
        val spec = ConceptSpec.defaultSpec()
        assertEquals(ConceptSpec.defaultSpec().toString(), spec.toString())
    }
}