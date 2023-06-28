package org.archguard.spec.lang

import org.junit.jupiter.api.Assertions.*

class ComposableSpecTest {
    @org.junit.jupiter.api.Test
    fun testDefaultSpec() {
        val spec = composable {
            channels {}
            integrations {}
            logics {}
            records {}
            capabilities {}
        }
        assertEquals(0, spec.exec(Scenario("test", "test")).size)
    }
}