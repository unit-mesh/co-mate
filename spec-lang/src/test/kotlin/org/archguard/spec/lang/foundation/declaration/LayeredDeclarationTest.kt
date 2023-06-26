package org.archguard.spec.lang.foundation.declaration

import org.archguard.spec.lang.matcher.shouldBe
import org.archguard.spec.lang.matcher.shouldNotBe
import org.junit.jupiter.api.Test

class LayeredDeclarationTest {
    @Test
    fun should_get_origin_text_in_string() {
        val declaration = layered {
            layer("interface") {
                pattern(".*\\.apis") { name shouldBe endsWith("Controller") }
            }
            layer("application") {
                pattern(".*\\.application") {
                    name shouldBe endsWith("DTO", "Request", "Response", "Factory", "Service")
                }
            }
            layer("domain") {
                pattern(".*\\.domain(?:\\.[a-zA-Z]+)?") { name shouldNotBe endsWith("Request", "Response") }
            }
            layer("infrastructure") {
                pattern(".*\\.infrastructure") { name shouldBe endsWith("Repository", "Mapper") }
            }

            dependency {
                "application" dependedOn "interface"
            }
        }

//        assertEquals(declaration.toString(), """""")
    }
}