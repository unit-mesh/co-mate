package org.archguard.spec.lang

import org.junit.jupiter.api.Assertions.*

class ConceptSpecTest {
    @org.junit.jupiter.api.Test
    fun testDefaultSpec() {
        val spec = ConceptSpec.defaultSpec()
        assertEquals(ConceptSpec.defaultSpec().toString(), spec.toString())
    }
}