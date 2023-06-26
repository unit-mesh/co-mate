package org.archguard.spec.lang.foundation.declaration

import org.archguard.spec.lang.matcher.shouldBe
import org.archguard.spec.lang.matcher.shouldNotBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LayeredDeclarationTest {
    @Test
    fun should_get_origin_text_in_string() {
        val declaration = layered {
            layer("interface") {
                pattern(".*\\.apis") { name shouldBe endWiths("Controller") }
            }
            layer("application") {
                pattern(".*\\.application") {
                    name shouldBe endWiths("DTO", "Request", "Response", "Factory", "Service")
                }
            }
            layer("domain") {
                pattern(".*\\.domain(?:\\.[a-zA-Z]+)?") { name shouldNotBe endWiths("Request", "Response") }
            }
            layer("infrastructure") {
                pattern(".*\\.infrastructure") { name shouldBe endWiths("Repository", "Mapper") }
            }

            dependency {
                "application" dependedOn "interface"
            }
        }

//        assertEquals(declaration.toString(), """""")
    }
}