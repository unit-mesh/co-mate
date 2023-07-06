package org.archguard.spec.lang

import org.junit.jupiter.api.Assertions.*

class ComposableSpecTest {
    @org.junit.jupiter.api.Test
    fun testDefaultSpec() {
        val spec = ComposableSpec().default()
        assertEquals(0, spec.exec(Scenario("test", "test")).size)
    }
}