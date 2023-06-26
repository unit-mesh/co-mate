package org.archguard.spec.lang.foundation.declaration

import org.archguard.spec.lang.matcher.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LayeredDefineTest {
    @Test
    fun should_get_origin_text_in_string() {
        val declaration = layered_t("interface") {
            pattern(".*\\.apis") { name shouldBe endsWith("Controller") }
        }

        assertEquals(
            declaration.toString(), """
        layered("interface") {
            pattern(".*\.apis") { name shouldBe endsWith("Controller") }
        }
        """.trimIndent()
        )
    }
}