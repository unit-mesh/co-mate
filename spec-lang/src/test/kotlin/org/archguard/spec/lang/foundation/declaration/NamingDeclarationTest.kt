package org.archguard.spec.lang.foundation.declaration

import org.archguard.spec.lang.matcher.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NamingDeclarationTest {
    @Test
    fun should_get_origin_text_in_string() {
        val declaration = naming_t {
            class_level {
                style("CamelCase")
                pattern(".*\\.apis") { name shouldBe endsWith("Controller") }
            }
        }

        assertEquals(
            declaration.toString(), """
            naming {
                class_level {
                    style("CamelCase")
                    pattern(".*\.apis") { name shouldBe endsWith("Controller") }
                }
            }
        """.trimIndent()
        )
    }

    @Test
    fun should_concat_with_class_function_levels() {
        val declaration = naming_t {
            class_level {
                style("CamelCase")
                pattern(".*\\.apis") { name shouldBe endsWith("Controller") }
            }
            function_level {
                style("SnakeCase")
                pattern(".*\\.apis") { name shouldBe endsWith("Controller") }
            }
        }

        assertEquals(
            declaration.toString(), """
            naming {
                class_level {
                    style("CamelCase")
                    pattern(".*\.apis") { name shouldBe endsWith("Controller") }
                }
                function_level {
                    style("SnakeCase")
                    pattern(".*\.apis") { name shouldBe endsWith("Controller") }
                }
            }
        """.trimIndent()
        )
    }
}