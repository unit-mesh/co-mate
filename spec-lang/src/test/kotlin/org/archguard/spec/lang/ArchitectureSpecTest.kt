package org.archguard.spec.lang

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ArchitectureSpecTest {
    @Test
    fun `should return empty result when no rule is defined`() {
        val spec = architecture {
            system("MySystem") {
                component("MyComponent") {
                    module("") {}
                }

                connection("WebServer" to "Database")
            }
        }

        val result = spec.exec("")

        assertTrue(result.isEmpty())
    }
}